package com.fakedonald.clickfarming.contorller.system

import com.fakedonald.clickfarming.domain.common.UserBankCard
import com.fakedonald.clickfarming.domain.merchant.Merchant
import com.fakedonald.clickfarming.domain.merchant.MerchantShop
import com.fakedonald.clickfarming.enums.StateTypeEnum
import com.fakedonald.clickfarming.enums.merchant.ShopTypeEnum
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import com.fakedonald.clickfarming.extension.*
import com.fakedonald.clickfarming.repository.UserBankCardRepository
import com.fakedonald.clickfarming.repository.merchant.MerchantRepository
import com.fakedonald.clickfarming.repository.merchant.MerchantShopRepository
import com.fakedonald.clickfarming.security.TokenService
import com.fakedonald.clickfarming.service.system.FundChangeRecordService
import jakarta.transaction.Transactional
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

/**
 * @author nathan
 * @date 2023/2/26 18:46
 *
 * 总后台商家Controller
 */
@RestController
@RequestMapping("/merchant")
class SystemMerchantController(
    private val merchantRepository: MerchantRepository,
    private val merchantShopRepository: MerchantShopRepository,
    private val fundChangeRecordService: FundChangeRecordService,
    private val userBankCardRepository: UserBankCardRepository,
    private val tokenService: TokenService,
) {


    /**
     * 商家店铺列表
     */
    @GetMapping("/queryMerchantShop")
    fun queryPage(request: MerchantShopRequest) {
        val pageRequest = generatePageRequest()

        var merchantIdList: List<Long>? = listOf()

        request.merchant?.let { param ->
            merchantIdList = merchantRepository.findAll(
                Merchant::username.like(param)
            ).map { it.id!! }
        }

        merchantShopRepository.findAll(
            and(
                merchantIdList?.let {
                    MerchantShop::merchantId.`in`(merchantIdList!!)
                },
                request.shopName?.let {
                    MerchantShop::shopName.like(it)
                },
                request.account?.let {
                    MerchantShop::account.equal(it)
                },
                request.state?.let {
                    val state = valueToEnum<StateTypeEnum>(it)
                    MerchantShop::state.equal(state)
                },
                request.shopType?.let {
                    val shopType = valueToEnum<ShopTypeEnum>(it)
                    MerchantShop::shopType.equal(shopType)
                }
            ),
            pageRequest
        ).toJson()
    }

    /**
     * 修改商家店铺
     */
    @PutMapping("/updateMerchantShop")
    fun updateMerchantShop(@RequestBody request: MerchantShop): Response {
        merchantRepository.findById(request.id!!).notFound()
        return merchantShopRepository.save(request).toJson()
    }

    /**
     * 切换商家启用状态
     */
    @PutMapping("/toggleEnabled/{id}")
    fun toggleEnabled(@PathVariable id: Long): Response {
        val updateEntity = merchantRepository.findById(id).notFound()
        updateEntity.enabled = !updateEntity.enabled
        return merchantRepository.save(updateEntity).toJson()
    }

    /**
     * 商家列表
     *
     * @param id: ID
     * @param username: 用户名
     */
    @GetMapping("/merchantList")
    fun merchantList(id: Long?, username: String?): Response {
        val pageRequest = generatePageRequest()
        return merchantRepository.findAll(
            and(
                Merchant::id.equal(id), username?.let { Merchant::username.like(it) }
            ),
            pageRequest
        )
            .toJson()
    }

    /**
     * 修改商家
     */
    @PutMapping("/updateMerchant")
    fun updateMerchant(@RequestBody request: Merchant): Response {
        if (request.id == null) return Response.error("参数错误")
        return merchantRepository.save(request).toJson()
    }

    /**
     * 商家银行卡列表
     */
    @GetMapping("/bankCardList")
    fun bankCardList(issueBank: String?, account: String?, state: String?): Response {
        val pageRequest = generatePageRequest()
        return userBankCardRepository.findAll(
            and(
                UserBankCard::userType.equal(UserTypeEnum.MERCHANT),
                UserBankCard::issueBank.equal(issueBank),
                UserBankCard::account.equal(account),
                state?.let {
                    val stateEnum = valueToEnum<StateTypeEnum>(it)
                    UserBankCard::state.equal(stateEnum)
                }
            ),
            pageRequest
        ).toJson()
    }

    /**
     * 修改商家余额
     */
    @Transactional
    @PutMapping("/updateMerchantBalance")
    fun updateMerchantBalance(@RequestBody request: UpdateMerchantBalanceRequest): Response {
        val updateMerchant = merchantRepository.findById(request.id).notFound()

        return if (request.operation.startsWith("+") || request.operation.startsWith("-")) {
            val amount: BigDecimal = BigDecimal.valueOf(request.operation.replace("+", "").trim().toDouble())
            updateMerchant.balance = updateMerchant.balance.plus(amount)

            // 保存资金变动记录
            fundChangeRecordService.saveFundChangeRecord(
                updateMerchant.username, UserTypeEnum.MERCHANT, updateMerchant.id!!,
                amount, request.remark, updateMerchant.balance,
                tokenService.getClaim("username") as String
            )

            merchantRepository.save(updateMerchant).toJson()
        } else Response.error("参数错误")
    }
}

/**
 * +, -, 商家余额
 */
data class UpdateMerchantBalanceRequest(
    // ID
    val id: Long,
    // 操作
    var operation: String,
    // 备注
    var remark: String?,
)

/**
 * 筛选条件
 */
data class MerchantShopRequest(
    // 商家用户名
    val merchant: String?,

    // 店铺名称
    val shopName: String?,

    // 掌柜号
    val account: String?,

    // 店铺类型
    val shopType: String?,

    // 状态
    val state: String?,
)













