package com.fakedonald.clickfarming.contorller.sales

import com.fakedonald.clickfarming.contorller.system.FundChangeRecordQueryRequest
import com.fakedonald.clickfarming.domain.common.UserBankCard
import com.fakedonald.clickfarming.domain.common.UserWithdrawRequest
import com.fakedonald.clickfarming.domain.merchant.Merchant
import com.fakedonald.clickfarming.domain.merchant.MerchantShop
import com.fakedonald.clickfarming.domain.sales.SalesMan
import com.fakedonald.clickfarming.domain.sales.SalesManCustomPrice
import com.fakedonald.clickfarming.domain.sales.SalesManMerchantCustomPrice
import com.fakedonald.clickfarming.enums.StateTypeEnum
import com.fakedonald.clickfarming.enums.sales.CommonPasswordTypeEnum
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import com.fakedonald.clickfarming.extension.*
import com.fakedonald.clickfarming.repository.UserBankCardRepository
import com.fakedonald.clickfarming.repository.UserWithdrawRequestRepository
import com.fakedonald.clickfarming.repository.merchant.MerchantRepository
import com.fakedonald.clickfarming.repository.merchant.MerchantShopRepository
import com.fakedonald.clickfarming.repository.sales.*
import com.fakedonald.clickfarming.security.TokenService
import com.fakedonald.clickfarming.service.system.FundChangeRecordService
import jakarta.transaction.Transactional
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal
import kotlin.jvm.optionals.getOrNull

/**
 * @author nathan
 * @date 2023/2/23 23:21
 */
@RestController
@RequestMapping("/sales")
class SalesController(
    private val tokenService: TokenService,
    private val salesManRepository: SalesManRepository,
    private val merchantRepository: MerchantRepository,
    private val userBankCardRepository: UserBankCardRepository,
    private val merchantShopRepository: MerchantShopRepository,
    private val fundChangeRecordService: FundChangeRecordService,
    private val userWithdrawRequestRepository: UserWithdrawRequestRepository,
    private val salesManCustomPriceRepository: SalesManCustomPriceRepository,
    private val commissionConfigRepository: CustomerCommissionConfigRepository,
    private val salesManMerchantCustomPriceRepository: SalesManMerchantCustomPriceRepository,
    val passwordEncoder: BCryptPasswordEncoder,
) {


    /**
     * ????????????????????????
     */
    @GetMapping("/currentUser")
    suspend fun getCurrentUser() = tokenService.getSalesManUser().toJson()

    /**
     * ????????????
     */
    @PutMapping("/updatePassword")
    suspend fun initialWithdrawPassword(@RequestBody request: SalesManResetPasswordRequest): Response {
        tokenService.resetSalesPassword(request)
        return Response.success()
    }

    /**
     * ?????????????????????
     */
    @GetMapping("/getMySalesMan")
    fun getMySalesMan(keyword: String?): Response {
        val pageRequest = generatePageRequest()
        val username = tokenService.getClaim("username") as String
        return salesManRepository.findAll(
            and(
                SalesMan::belongsToUser.equal(username),
                keyword?.let {
                    SalesMan::username.like(keyword)
                }
            ),
            pageRequest
        ).toJson()
    }

    /**
     * ?????????????????????, ?????????
     */
    @GetMapping("/getMySalesManList")
    fun getMySalesManList(): Response {
        val username = tokenService.getClaim("username") as String
        return salesManRepository.findAll(
            SalesMan::belongsToUser.equal(username)
        ).toJson()
    }

    /**
     * ???????????????
     */
    @PostMapping("/addSalesMan")
    fun addSalesMan(username: String): Response {
        val count = salesManRepository.count(SalesMan::username.equal(username))
        if (count != 0L) throw CustomException("?????????${username}?????????")
        val salesManUser = tokenService.getSalesManUser()
        val newEntity = SalesMan(
            username = username,
            belongsToUser = salesManUser.username,
            subSiteId = salesManUser.subSiteId,
            defaultWithdrawPassword = true,
            password = passwordEncoder.encode("123456"),
            withdrawPassword = passwordEncoder.encode("123456"),
        )

        return salesManRepository.save(newEntity).toJson()
    }

    /**
     *  ???????????????
     */
    @PutMapping("/updateSalesRemark")
    fun updateSalesRemark(@RequestBody request: UpdateRemarkRequest): Response {
        val username = tokenService.getClaim("username") as String
        val entity = salesManRepository.findById(request.id).notFound()
        if (username != entity.belongsToUser) return Response.error(message = "????????????")
        entity.remark = request.remark
        return salesManRepository.save(entity).toJson()
    }

    /**
     * ???????????????????????????
     */
    @PutMapping("/toggleEnabled/{id}")
    fun toggleEnabled(@PathVariable id: Long): Response {
        val username = tokenService.getClaim("username") as String
        val entity = salesManRepository.findById(id).notFound()
        if (username != entity.belongsToUser) return Response.error(message = "????????????")
        entity.enabled = !entity.enabled
        return salesManRepository.save(entity).toJson()
    }

    /**
     * ????????????????????????
     */
    @GetMapping("/getMyMerchant")
    fun getMyMerchant(merchantUsername: String?, salesManUsername: String?): Response {
        val pageRequest = generatePageRequest()

        val salesManIdList = getSubSalesIdList(salesManUsername)

        return merchantRepository.findAll(
            and(
                Merchant::salesManId.`in`(salesManIdList),
                merchantUsername?.let {
                    Merchant::username.like(it)
                },
            ),
            pageRequest
        ).toJson()
    }

    /**
     * ?????????????????????
     */
    @GetMapping("/getMyMerchantList")
    fun getMyMerchantList(): Response {
        val salesManIdList = getSubSalesIdList()
        return merchantRepository.findAll(
            Merchant::salesManId.`in`(salesManIdList)
        ).toJson()
    }

    /**
     * ??????????????????
     */
    @PutMapping("/updateMerchantRemark")
    fun updateMerchantRemark(@RequestBody request: UpdateRemarkRequest): Response {
        val salesManIdList = getSubSalesIdList()
        val entity = merchantRepository.findById(request.id).notFound()
        if (!salesManIdList.any { it == entity.salesManId }) throw CustomException("????????????")
        entity.remark = request.remark
        return merchantRepository.save(entity).toJson()
    }

    /**
     * ??????????????????
     */
    @GetMapping("/getShopList")
    fun getShopList(request: ShopListRequest): Response {
        val pageRequest = generatePageRequest()
        val salesManIdList = getSubSalesIdList(request.salesManUsername)

        val merchantIdList = merchantRepository.findAll(
            and(
                Merchant::salesManId.`in`(salesManIdList),
                request.merchantUsername?.let {
                    Merchant::username.like(it)
                },
            )
        ).map { it.id!! }


        return merchantShopRepository.findAll(
            and(
                MerchantShop::merchantId.`in`(merchantIdList),
                request.shopName?.let {
                    MerchantShop::shopName.like(it)
                }
            ),
            pageRequest
        ).toJson()

    }

    /**
     * ???????????????????????????
     */
    @GetMapping("/getMyBankCardList")
    fun getMyBankCardList(): Response {
        val userId = tokenService.getClaim("userId") as Long
        return userBankCardRepository.findAll(
            and(
                UserBankCard::userType.equal(UserTypeEnum.SALES_MAN),
                UserBankCard::userId.equal(userId)
            )
        ).toJson()
    }

    /**
     * ????????????????????????
     */
    @PostMapping("/addBankCard")
    fun addBankCard(@RequestBody request: UserBankCard): Response {
        val userId = tokenService.getClaim("userId") as Long
        request.state = StateTypeEnum.PASS
        request.userType = UserTypeEnum.SALES_MAN
        request.userId = userId
        return userBankCardRepository.save(request).toJson()
    }

    /**
     * ????????????????????????
     */
    @PutMapping("/updateBankCard")
    fun updateBankCard(@RequestBody request: UserBankCard): Response {
        request.id?.let {
            val updateEntity = userBankCardRepository.findById(it).notFound()
            updateEntity.issueBank = request.issueBank
            updateEntity.account = request.account
            updateEntity.bankType = request.bankType
            updateEntity.realName = request.realName
            return userBankCardRepository.save(updateEntity).toJson()
        } ?: return Response.error()
    }

    /**
     * ????????????????????????
     */
    @DeleteMapping("/userBankCard/{id}")
    fun deleteUserBankCard(@PathVariable id: Long): Response {
        val userId = tokenService.getClaim("userId") as Long
        val deleteEntity = userBankCardRepository.findById(id).notFound()
        if (deleteEntity.userId == userId) {
            userBankCardRepository.deleteById(id)
        } else throw CustomException("????????????")
        return Response.success()
    }

    /**
     * ???????????????????????????
     *
     * @param request ??????
     */
    @GetMapping("/fundChangRecord")
    fun queryData(request: FundChangeRecordQueryRequest): Response {
        val userId = tokenService.getClaim("userId") as Long
        request.userId = userId
        request.userType = UserTypeEnum.SALES_MAN
        return fundChangeRecordService.queryPage(request).toJson()
    }

    /**
     * ????????????
     */
    @PostMapping("/applyWithdraw")
    @Transactional
    fun applyWithdraw(@RequestBody request: UserWithdrawRequest): Response {
        val user = tokenService.getSalesManUser()
        return if (passwordEncoder.matches(request.withdrawPassword, user.withdrawPassword)) {
            if (user.balance < request.amount) return Response.error(null, "????????????")
            else {
                val userBankCard = userBankCardRepository.findById(request.bankCardId).notFound()
                if (userBankCard.userId != user.id) return Response.error(null, "????????????")
                request.userId = user.id!!
                request.userType = UserTypeEnum.SALES_MAN
                userWithdrawRequestRepository.save(request)
                // ??????????????????
                user.balance = user.balance - request.amount
                user.frozeBalance = user.frozeBalance!! + request.amount
                salesManRepository.save(user)
                Response.success()
            }
        } else Response.error(null, "????????????")
    }

    /**
     * ????????????, ??????
     */
    @GetMapping("/myCustomPrice")
    fun myCustomPrice(username: String?): Response {
        val pageRequest = generatePageRequest()
        val currentUser = tokenService.getSalesManUser()
        if (!currentUser.subSite!!) throw CustomException("401, ????????????")
        val customPriceList = salesManCustomPriceRepository.findAll(
            and(
                SalesManCustomPrice::subSiteId.equal(currentUser.subSiteId),
                SalesManCustomPrice::username.equal(username)
            ),
            pageRequest
        )
        return customPriceList.toJson()
    }

    /**
     * ?????? / ????????????
     */
    @OptIn(ExperimentalStdlibApi::class)
    @PostMapping("/saveCustomPrice")
    fun addCustomPrice(@RequestBody request: SalesManCustomPrice): Response {
        val currentUser = tokenService.getSalesManUser()
        if (!currentUser.subSite!!) throw CustomException("401, ????????????")
        val customPrice = salesManCustomPriceRepository.findOne(SalesManCustomPrice::username.equal(request.username)).getOrNull()
        customPrice?.let {
            // ??????
            it.configList = request.configList
            return salesManCustomPriceRepository.save(it).toJson()
        }

        request.subSiteId = currentUser.subSiteId
        return salesManCustomPriceRepository.save(request).toJson()
    }

    /**
     * ??????????????????
     */
    @GetMapping("/merchantCustomPriceList")
    fun merchantCustomPrice(username: String?): Response {
        val pageRequest = generatePageRequest()
        val currentUser = tokenService.getSalesManUser()
        if (!currentUser.subSite!!) throw CustomException("401, ????????????")

        return salesManMerchantCustomPriceRepository.findAll(
            SalesManMerchantCustomPrice::username.equal(username),
            pageRequest
        ).toJson()
    }

    /**
     * ??????ID??????
     */
    @GetMapping("/queryMerchantCustomPrice/{id}")
    fun queryMerchantCustomPrice(@PathVariable id: Long?): Response {
        var salesManMerchantCustomPrice: SalesManMerchantCustomPrice? = null

        val res: MutableList<SalesManMerchantCustomPriceDto> = mutableListOf()

        id?.let {
            salesManMerchantCustomPrice = salesManMerchantCustomPriceRepository.findById(it).notFound()
        }

        val commissionConfigList = commissionConfigRepository.findAll()

        val commissionConfigMap = commissionConfigList.associateBy { it.taskType }

        // ???????????????
        TaskTypeEnum.values().forEach {
            val commissionConfig = commissionConfigMap[it]
            commissionConfig?.let { commission ->
                val dto = SalesManMerchantCustomPriceDto(it,
                    buildList {
                        commission.configList.forEachIndexed { index, item ->
                            val merchantCustomPriceMap = salesManMerchantCustomPrice?.configList?.associateBy { customPrice -> customPrice.taskType }
                            var inputNumber = 0.toBigDecimal()
                            if (merchantCustomPriceMap != null) {
                                inputNumber = merchantCustomPriceMap[it]?.customPriceList?.get(index) ?: 0.toBigDecimal()
                            }
                            this.add(
                                SalesManMerchantCustomPriceData(item.startPrice, item.endPrice, 0.toBigDecimal(), item.instantServiceFee, inputNumber)
                            )
                        }
                    })
                res.add(dto)
            } ?: run {
                val dto = SalesManMerchantCustomPriceDto(it, listOf())
                res.add(dto)
            }
        }

        return res.toJson()
    }

    /**
     * ??????????????????
     */
    @PostMapping("/saveMerchantCustomPrice")
    fun addMerchantCustomPrice(@RequestBody request: SalesManMerchantCustomPrice): Response {
        val currentUser = tokenService.getSalesManUser()
        request.subSiteId = currentUser.subSiteId
        val updateEntity = salesManMerchantCustomPriceRepository.findOne(
            and(
                SalesManMerchantCustomPrice::subSiteId.equal(request.subSiteId),
                SalesManMerchantCustomPrice::username.equal(request.username)
            )
        )

        updateEntity.ifPresentOrElse({
            it.configList = request.configList
            salesManMerchantCustomPriceRepository.save(it)
        }, {
            salesManMerchantCustomPriceRepository.save(request)
        })

        return Response.success()
    }

    // ????????????
    /**
     * ??????????????????
     *
     * @param username ????????????
     */
    private fun getSubSalesIdList(username: String? = null): List<Long> {
        val salesMan = tokenService.getSalesManUser()
        val salesManIdList = buildList {
            if (salesMan.subSite == true) this.addAll(
                salesManRepository
                    .findAll(
                        and(
                            SalesMan::subSiteId.equal(salesMan.subSiteId),
                            username?.let {
                                SalesMan::username.like(it)
                            }
                        )

                    )
                    .map { it.id!! }
            )
            else this.add(salesMan.id!!)
        }
        return salesManIdList
    }

}

//????????????
data class UpdateRemarkRequest(
    val id: Long,
    val remark: String,
)

data class SalesManMerchantCustomPriceDto(
    // ????????????
    var taskType: TaskTypeEnum,
    // ????????????
    var dataList: List<SalesManMerchantCustomPriceData>,
)

data class SalesManMerchantCustomPriceData(
    // ????????????
    var startPrice: BigDecimal,
    var endPrice: BigDecimal,
    // ?????????
    var price: BigDecimal,
    var price2: BigDecimal,

    // ???????????????
    var serviceFeeInput: BigDecimal,
)

// ??????????????????
data class ShopListRequest(
    // ???????????????
    var merchantUsername: String?,
    // ??????????????????
    var salesManUsername: String?,
    // ?????????
    var shopName: String?,
)

/**
 * ?????????????????????`
 */
data class SalesManResetPasswordRequest(
    // ????????????
    val passwordType: CommonPasswordTypeEnum,
    // ?????????
    val originalPassword: String?,
    // ?????????
    val newPassword: String,
    // ????????????
    val confirmPassword: String,
)