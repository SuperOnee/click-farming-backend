package com.fakedonald.clickfarming.service.system

import com.fakedonald.clickfarming.contorller.system.AlterFundRequest
import com.fakedonald.clickfarming.contorller.system.CommissionConfigUpdateRequest
import com.fakedonald.clickfarming.contorller.system.SalesManQueryParams
import com.fakedonald.clickfarming.contorller.system.SaveRequest
import com.fakedonald.clickfarming.domain.FundChangeRecord
import com.fakedonald.clickfarming.domain.sales.*
import com.fakedonald.clickfarming.enums.sales.SalesManTypeEnum
import com.fakedonald.clickfarming.enums.sales.TradeTypeEnum
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import com.fakedonald.clickfarming.extension.*
import com.fakedonald.clickfarming.repository.sales.SalesManCustomPriceRepository
import com.fakedonald.clickfarming.repository.sales.SalesManMerchantCustomPriceRepository
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
    val fundChangeRecordService: FundChangeRecordService,
    val subSiteRepository: SalesManSubSiteRepository,
    val tokenService: TokenService,
    val salesManCustomPriceRepository: SalesManCustomPriceRepository,
    val salesManMerchantCustomPriceRepository: SalesManMerchantCustomPriceRepository,
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
                    // ?????????
                    SalesMan::username.equal(queryParams.username),
                    // ????????????
                    SalesMan::id.equal(queryParams.id),
                    when (salesManType) {
                        // ??????
                        SalesManTypeEnum.ALL -> emptySpecification()
                        // ??????
                        SalesManTypeEnum.SUB -> SalesMan::subSite.equal(true)
                        // ?????????
                        SalesManTypeEnum.MAIN_PLATFORM -> SalesMan::salesManType.equal(SalesManTypeEnum.MAIN_PLATFORM)
                        // ?????????
                        SalesManTypeEnum.SUB_PLATFORM -> {
                            and(
                                SalesMan::subSiteId.isNotNull(),
                                // ????????????
                                SalesMan::subSiteId.`in`(subSiteIdList)
                            )
                        }
                    },
                ),
                pageRequest
            )
    }

    /**
     * ??????, ???????????????
     */
    @Transactional
    override suspend fun saveEntity(request: SaveRequest): SalesMan {
        // ??????
        request.id?.let {
            val entity = salesManRepository.findById(it).notFound()
            salesManRepository.findOne(SalesMan::username.equal(request.username)).ifPresent { e ->
                if (it != entity.id) throw CustomException("${e.username}?????????")
            }
            entity.sharePermission = request.sharePermission
            entity.businessRate = request.businessRate
            entity.customerRate = request.customerRate
            return salesManRepository.save(entity)
        }
        // ???????????????????????????
        salesManRepository.findOne(SalesMan::username.equal(request.username)).ifPresent {
            throw CustomException("${it.username}?????????")
        }.run {
            // ??????
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
     * ???????????????
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
     * ??????????????????
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
     * ?????????????????????
     */
    @Transactional
    override suspend fun updateFund(request: AlterFundRequest): SalesMan {
        val admin = tokenService.getAdminUser()
        val valid =
            tokenService.validateAdminPassword(admin, request.fundOperatePassword, AdminPasswordTypeEnum.FUND_OPERATION)
        if (!valid) throw CustomException("??????????????????")

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

            // ????????????????????????
            fundChangeRecordService.saveFundChangeRecord(
                entity.username, UserTypeEnum.SALES_MAN,
                entity.id!!, amount, request.remark, entity.balance, admin.username
            )

            // ??????????????????
            entity.balance = entity.balance.plus(amount)
            salesManRepository.save(entity)
        }
    }


    @Transactional
    override suspend fun openSubSite(userId: Long) {
        val salesMan = withContext(Dispatchers.IO) {
            salesManRepository.findById(userId)
        }.notFound()

        // ?????????????????????
        val subUserCount = salesManRepository.count(SalesMan::belongsToUser.equal(salesMan.username))

        if (subUserCount > 0) throw CustomException("??????????????????????????????, ??????????????????!")
        else {
            salesMan.subSite = true

            withContext(Dispatchers.IO) {
                val subSite = subSiteRepository.save(SalesManSubSite(siteName = salesMan.username))
                salesMan.subSiteId = subSite.id
                val savedEntity = salesManRepository.save(salesMan)
                // ??????????????????
                val configList = buildList {
                    for (i in 1..24) {
                        this.add(SalesManCustomPriceConfigItem(0.5.toBigDecimal(), false))
                    }
                }

                val customPrice = SalesManCustomPrice(username = "??????", subSiteId = savedEntity.subSiteId, configList = configList)

                val merchantCustomPriceList = buildList {
                    TaskTypeEnum.values().forEach {
                        this.add(MerchantCustomPriceConfigItem(it, listOf()))
                    }
                }

                val merchantCustomPrice = SalesManMerchantCustomPrice(username = "??????", subSiteId = savedEntity.subSiteId, configList = merchantCustomPriceList)

                salesManMerchantCustomPriceRepository.save(merchantCustomPrice)
                salesManCustomPriceRepository.save(customPrice)
            }
        }
    }


    /**
     * ???????????????
     */
    private fun checkAndGenerateShareCode(): String {
        var shareCode = generateRandomCode(6)

        // ???????????????????????????
        var present = salesManRepository.findOne(SalesMan::shareCode.equal(shareCode)).isPresent
        var limit = 0
        while (present) {
            limit++
            if (limit >= 5) throw CustomException("?????????????????????, ?????????")
            shareCode = generateRandomCode(6)
            present = salesManRepository.findOne(SalesMan::shareCode.equal(shareCode)).isPresent
        }

        return shareCode
    }

}