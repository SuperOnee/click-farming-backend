package com.fakedonald.clickfarming.service.system

import com.fakedonald.clickfarming.contorller.system.AlterFundRequest
import com.fakedonald.clickfarming.contorller.system.CommissionConfigUpdateRequest
import com.fakedonald.clickfarming.contorller.system.SalesManQueryParams
import com.fakedonald.clickfarming.contorller.system.SaveRequest
import com.fakedonald.clickfarming.domain.FundChangeRecord
import com.fakedonald.clickfarming.domain.sales.SalesMan
import com.fakedonald.clickfarming.domain.sales.SalesManSubSite
import com.fakedonald.clickfarming.enums.sales.SalesManTypeEnum
import com.fakedonald.clickfarming.enums.sales.TradeTypeEnum
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import com.fakedonald.clickfarming.extension.*
import com.fakedonald.clickfarming.repository.sales.SalesManRepository
import com.fakedonald.clickfarming.repository.sales.SalesManSubSiteRepository
import com.fakedonald.clickfarming.repository.system.FundChangeRecordRepository
import com.fakedonald.clickfarming.security.AdminPasswordTypeEnum
import com.fakedonald.clickfarming.security.TokenService
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.random.Random

/**
 * @author nathan
 * @date 2023/2/10 01:20
 */
interface SalesManService {

    suspend fun queryPage(queryParams: SalesManQueryParams): Page<SalesMan>
    suspend fun saveEntity(request: SaveRequest): SalesMan
    suspend fun resetPassword(id: Long): SalesMan
    suspend fun toggleEntityEnable(id: Long): Boolean
    suspend fun refreshEntityShareCode(id: Long): String
    suspend fun updateCommissionConfig(id: Long, request: CommissionConfigUpdateRequest): SalesMan
    suspend fun updateFund(request: AlterFundRequest): SalesMan
    suspend fun openSubSite(userId: Long)
}

@Service
class SalesManServiceImpl(
    val salesManRepository: SalesManRepository,
    val fundChangeRecordRepository: FundChangeRecordRepository,
    val fundChangeRecordService: FundChangeRecordService,
    val subSiteRepository: SalesManSubSiteRepository,
    val tokenService: TokenService,
    val passwordEncoder: BCryptPasswordEncoder
) : SalesManService {
    override suspend fun queryPage(queryParams: SalesManQueryParams): Page<SalesMan> {
        val pageRequest = generatePageRequest()
        val salesManType = valueToEnum<SalesManTypeEnum>(queryParams.salesType ?: SalesManTypeEnum.ALL.value)

        var subSiteIdList = listOf<Long>()

        queryParams.subSite?.let { param ->
            subSiteIdList = subSiteRepository
                .findAll(SalesManSubSite::siteName.equal(param)).toList()
                .map { it.id ?: 0L }
        }

        return salesManRepository
            .findAll(
                and(
                    // 用户名
                    SalesMan::username.equal(queryParams.username),
                    // 用户编号
                    SalesMan::id.equal(queryParams.id),
                    when (salesManType) {
                        // 全部
                        SalesManTypeEnum.ALL -> emptySpecification()
                        // 分站
                        SalesManTypeEnum.SUB -> SalesMan::subSite.equal(true)
                        // 总站下
                        SalesManTypeEnum.MAIN_PLATFORM -> SalesMan::salesManType.equal(SalesManTypeEnum.MAIN_PLATFORM)
                        // 分站下
                        SalesManTypeEnum.SUB_PLATFORM -> {
                            and(
                                SalesMan::subSiteId.isNotNull(),
                                // 分站名称
                                SalesMan::subSiteId.`in`(subSiteIdList)
                            )
                        }
                    },
                ),
                pageRequest
            )
    }

    /**
     * 新增, 设置业务员
     */
    @Transactional
    override suspend fun saveEntity(request: SaveRequest): SalesMan {
        // 更新
        request.id?.let {
            val entity = salesManRepository.findById(it).notFound()
            salesManRepository.findOne(SalesMan::username.equal(request.username)).ifPresent { e ->
                if (it != entity.id) throw CustomException("${e.username}已存在")
            }
            entity.sharePermission = request.sharePermission
            entity.businessRate = request.businessRate
            entity.customerRate = request.customerRate
            return salesManRepository.save(entity)
        }
        // 判断用户名是否重复
        salesManRepository.findOne(SalesMan::username.equal(request.username)).ifPresent {
            throw CustomException("${it.username}已存在")
        }.run {
            // 新增
            val configList = buildList {
                for (i in 1..24) add(BigDecimal.ZERO)
            }
            val newEntity = SalesMan(
                username = request.username,
                sharePermission = request.sharePermission,
                businessRate = request.businessRate,
                customerRate = request.customerRate,
                password = passwordEncoder.encode("123456"),
                businessCommissionConfig = configList,
                customerCommissionConfig = configList,
                withdrawPassword = passwordEncoder.encode("123456"),
                defaultWithdrawPassword = true,
            )

            val shareCode = checkAndGenerateShareCode()

            newEntity.shareCode = shareCode

            return salesManRepository.save(newEntity)
        }
    }

    @Transactional
    override suspend fun resetPassword(id: Long): SalesMan {
        withContext(Dispatchers.IO) {
            salesManRepository.findById(id)
        }.notFound()
            .also {
                it.password = passwordEncoder.encode("123456")
                it.withdrawPassword = passwordEncoder.encode("123456")
                it.defaultWithdrawPassword = true
                return salesManRepository.save(it)
            }
    }

    @Transactional
    override suspend fun toggleEntityEnable(id: Long): Boolean {
        withContext(Dispatchers.IO) {
            salesManRepository.findById(id)
        }.notFound()
            .let {
                it.enabled = !it.enabled
                salesManRepository.save(it)
                return it.enabled
            }
    }

    /**
     * 刷新邀请码
     */
    @Transactional
    override suspend fun refreshEntityShareCode(id: Long): String {
        val shareCode = checkAndGenerateShareCode()
        withContext(Dispatchers.IO) {
            salesManRepository.findById(id)
        }.notFound()
            .let {
                it.shareCode = shareCode
                salesManRepository.save(it)
                return it.shareCode ?: ""
            }
    }

    /**
     * 抽佣配置更新
     */
    @Transactional
    override suspend fun updateCommissionConfig(id: Long, request: CommissionConfigUpdateRequest): SalesMan {

        val entity = withContext(Dispatchers.IO) {
            salesManRepository.findById(id)
        }.notFound()

        return withContext(Dispatchers.IO) {
            request.businessConfig?.let {
                if (it.size == 12) entity.businessCommissionConfig = it
            }

            request.customerConfig?.let {
                if (it.size == 12) entity.customerCommissionConfig = it
            }
            salesManRepository.save(entity)
        }
    }

    /**
     * 更新业务员余额
     */
    @Transactional
    override suspend fun updateFund(request: AlterFundRequest): SalesMan {
        val admin = tokenService.getAdminUser()
        val valid =
            tokenService.validateAdminPassword(admin, request.fundOperatePassword, AdminPasswordTypeEnum.FUND_OPERATION)
        if (!valid) throw CustomException("操作密码错误")

        return withContext(Dispatchers.IO) {
            val entity = salesManRepository.findById(request.id).notFound()
            val amount =
                if (request.operation.contains("-")) BigDecimal.valueOf(request.operation.toLong())
                else BigDecimal.valueOf(
                    request.operation.replace(
                        "+",
                        ""
                    ).toLong()
                )

            // 保存资金变动记录
            fundChangeRecordService.saveFundChangeRecord(
                entity.username, UserTypeEnum.SALES_MAN,
                entity.id!!, amount, request.remark, entity.balance, admin.username
            )

            // 修改用户资金
            entity.balance = entity.balance.plus(amount)
            salesManRepository.save(entity)
        }
    }


    @Transactional
    override suspend fun openSubSite(userId: Long) {
        val salesMan = withContext(Dispatchers.IO) {
            salesManRepository.findById(userId)
        }.notFound()

        // 判断是否有下级
        val subUserCount = salesManRepository.count(SalesMan::belongsToUser.equal(salesMan.belongsToUser))

        if (subUserCount > 0) throw CustomException("该业务员已经发展下级, 不可开启分站!")
        else {
            salesMan.subSite = true

            withContext(Dispatchers.IO) {
                val subSite = subSiteRepository.save(SalesManSubSite(siteName = salesMan.username))
                salesMan.subSiteId = subSite.id
                salesManRepository.save(salesMan)
            }
        }
    }


    /**
     * 检索分享码
     */
    private fun checkAndGenerateShareCode(): String {
        var shareCode = generateRandomCode(6)

        // 查询邀请码是否重复
        var present = salesManRepository.findOne(SalesMan::shareCode.equal(shareCode)).isPresent
        var limit = 0
        while (present) {
            limit++
            if (limit >= 5) throw CustomException("新增业务员错误, 请重试")
            shareCode = generateRandomCode(6)
            present = salesManRepository.findOne(SalesMan::shareCode.equal(shareCode)).isPresent
        }

        return shareCode
    }

}