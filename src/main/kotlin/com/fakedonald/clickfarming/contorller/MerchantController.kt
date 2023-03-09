package com.fakedonald.clickfarming.contorller

import com.fakedonald.clickfarming.contorller.system.FundChangeRecordQueryRequest
import com.fakedonald.clickfarming.domain.common.UserBankCard
import com.fakedonald.clickfarming.domain.common.UserWithdrawRequest
import com.fakedonald.clickfarming.domain.merchant.MerchantShop
import com.fakedonald.clickfarming.domain.merchant.MerchantTopUpRecord
import com.fakedonald.clickfarming.enums.StateTypeEnum
import com.fakedonald.clickfarming.enums.sales.CommonPasswordTypeEnum
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import com.fakedonald.clickfarming.extension.*
import com.fakedonald.clickfarming.repository.UserBankCardRepository
import com.fakedonald.clickfarming.repository.UserWithdrawRequestRepository
import com.fakedonald.clickfarming.repository.merchant.MerchantRepository
import com.fakedonald.clickfarming.repository.merchant.MerchantShopRepository
import com.fakedonald.clickfarming.repository.merchant.MerchantTopUpRecordRepository
import com.fakedonald.clickfarming.security.TokenService
import com.fakedonald.clickfarming.service.system.FundChangeRecordService
import com.fakedonald.clickfarming.service.system.SystemBankCardService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*


/**
 * @author nathan
 * @date 2023/2/26 18:35
 *
 * 商家接口
 */
@RestController
@RequestMapping("/merchantApi")
class MerchantController(
    private val merchantShopRepository: MerchantShopRepository,
    private val userBankCardRepository: UserBankCardRepository,
    private val fundChangeRecordService: FundChangeRecordService,
    private val systemBankCardService: SystemBankCardService,
    private val merchantTopUpRecordRepository: MerchantTopUpRecordRepository,
    private val userWithdrawRequestRepository: UserWithdrawRequestRepository,
    private val passwordEncoder: BCryptPasswordEncoder,
    val tokenService: TokenService, private val merchantRepository: MerchantRepository,
) {


    /**
     * 获取店铺列表
     *
     * @param shopType 店铺类型
     */
    @GetMapping("/getMyShopList")
    fun queryData(shopType: String?): Response {
        val userId = tokenService.getClaim("userId") as Long
        return merchantShopRepository.findAll(
            and(
                MerchantShop::merchantId.equal(userId),
                shopType?.let {
                    MerchantShop::shopType.equal(valueToEnum(shopType))
                }
            )
        ).toJson()
    }

    /**
     * 添加店铺
     */
    @PostMapping("/addShop")
    fun addShop(@RequestBody request: MerchantShop): Response {
        val userId = tokenService.getClaim("userId") as Long
        request.merchantId = userId
        request.state = StateTypeEnum.PENDING
        return merchantShopRepository.save(request).toJson()
    }

    // 商家添加银行卡
    @PostMapping("/addBankCard")
    fun addBankCard(@RequestBody request: UserBankCard): Response {
        val userId = tokenService.getClaim("userId") as Long
        request.userId = userId
        request.userType = UserTypeEnum.MERCHANT
        request.state = StateTypeEnum.PENDING
        return userBankCardRepository.save(request).toJson()
    }

    /**
     * 商家修改银行卡
     */
    @PostMapping("/updateBankCard")
    fun updateBankCard(@RequestBody request: UserBankCard): Response {
        val userId = tokenService.getClaim("userId") as Long
        val updateUserBankCard = userBankCardRepository.findById(request.id!!).notFound()
        if (updateUserBankCard.userId != userId && updateUserBankCard.userType != UserTypeEnum.MERCHANT)
            throw CustomException("参数错误")
        if (updateUserBankCard.state == StateTypeEnum.PASS)
            throw CustomException("该银行卡已经审核通过不允许更改")
        updateUserBankCard.bankType = request.bankType
        updateUserBankCard.account = request.account
        updateUserBankCard.realName = request.realName
        updateUserBankCard.state = StateTypeEnum.PENDING
        return userBankCardRepository.save(updateUserBankCard).toJson()
    }

    /**
     * 商家删除银行卡
     */
    @DeleteMapping("/deleteBankCard/{id}")
    fun deleteBankCard(@PathVariable id: Long): Response {
        val userId = tokenService.getClaim("userId") as Long
        val userBankCard = userBankCardRepository.findById(id).notFound()
        if (userBankCard.userId != userId && userBankCard.userType != UserTypeEnum.MERCHANT)
            throw CustomException("参数错误")
        userBankCardRepository.deleteById(id)
        return Response.success()
    }

    /**
     * 我的银行卡列表
     */
    @GetMapping("/getMyBankCardList")
    fun bankCardList(): Response {
        val userId = tokenService.getClaim("userId") as Long
        return userBankCardRepository.findAll(
            and(
                UserBankCard::userType.equal(UserTypeEnum.MERCHANT),
                UserBankCard::userId.equal(userId),
            )
        ).toJson()
    }

    /**
     * 商家资金明细
     *
     * @param request 请求
     */
    @GetMapping("/fundChangRecord")
    fun fundChangeRecord(request: FundChangeRecordQueryRequest): Response {
        val userId = tokenService.getClaim("userId") as Long
        request.userId = userId
        request.userType = UserTypeEnum.MERCHANT
        return fundChangeRecordService.queryPage(request).toJson()
    }

    /**
     * 更新商家密码
     *
     * @param request 请求
     */
    @PutMapping("/updateWithdrawPassword")
    fun updateWithdrawPassword(request: MerchantPasswordRequest): Response {
        tokenService.resetMerchantPassword(request)
        return Response.success()
    }

    /**
     * 修改个人信息
     *
     * @param request: 请求
     */
    @PutMapping("/updateInfo")
    fun updateInfo(@RequestBody request: MerchantInfoUpdateRequest): Response {
        val currentUser = tokenService.getMerchantUser()
        currentUser.contact = request.contact
        currentUser.contactNumber = request.contactNumber
        currentUser.qq = request.qq
        currentUser.wechat = request.wechat
        currentUser.mainCategory = request.mainCategory
        currentUser.province = request.province
        currentUser.city = request.city
        return merchantRepository.save(currentUser).toJson()
    }

    /**
     * 系统默认收款账号查询
     */
    @GetMapping("/systemReceiveAccount")
    fun systemReceiveAccount(): Response = systemBankCardService.queryList().single { it.isDefault }.toJson()

    /**
     * 提交充值
     */
    @PostMapping("/merchantTopUp")
    fun merchantTopUp(@RequestBody request: MerchantTopUpRecord): Response {
        request.state = StateTypeEnum.PENDING
        return merchantTopUpRecordRepository.save(request).toJson()
    }

    /**
     * 充值记录列表
     */
    @GetMapping("/merchantTopUp")
    fun merchantTopUpList(): Response {
        val pageRequest = generatePageRequest()
        val userId = tokenService.getClaim("userId") as Long
        val pagedData = merchantTopUpRecordRepository.findAll(
            MerchantTopUpRecord::merchantId.equal(userId),
            pageRequest
        )

        val bankCardIdList = pagedData.content.map(MerchantTopUpRecord::bankCardId).toList()
        val bankCardMap = userBankCardRepository.findAll(
            UserBankCard::id.`in`(bankCardIdList)
        ).associateBy { it.id }

        pagedData.content.forEach { it.account = bankCardMap[it.bankCardId]?.account }

        return pagedData.toJson()
    }

    /**
     * 申请提现
     */
    @PostMapping("/applyWithdraw")
    fun applyWithdraw(@RequestBody request: UserWithdrawRequest): Response {
        val user = tokenService.getMerchantUser()
        return if (passwordEncoder.matches(request.withdrawPassword, user.withdrawPassword)) {
            if (user.balance < request.amount) return Response.error(null, "余额不足")
            else {
                val userBankCard = userBankCardRepository.findById(request.bankCardId).notFound()
                if (userBankCard.userId != user.id) return Response.error(null, "参数错误")
                request.userId = user.id!!
                request.userType = UserTypeEnum.MERCHANT
                userWithdrawRequestRepository.save(request)
                // 更新用户余额
                user.balance = user.balance - request.amount
                user.frozeBalance = user.frozeBalance + request.amount
                merchantRepository.save(user)
                Response.success()
            }
        } else Response.error(null, "密码错误")
    }

}

/**
 * 修改密码请求体`
 */
data class MerchantPasswordRequest(
    // 密码类型
    val passwordType: CommonPasswordTypeEnum,
    // 原密码
    val originalPassword: String?,
    // 新密码
    val newPassword: String,
    // 确认密码
    val confirmPassword: String,
)

data class MerchantInfoUpdateRequest(
    var contact: String? = null,
    var contactNumber: String? = null,
    var qq: String? = null,
    var wechat: String? = null,
    var mainCategory: String? = null,
    var province: String? = null,
    var city: String? = null,
)




















