package com.fakedonald.clickfarming.contorller.sales

import com.fakedonald.clickfarming.domain.common.UserBankCard
import com.fakedonald.clickfarming.domain.merchant.Merchant
import com.fakedonald.clickfarming.domain.merchant.MerchantShop
import com.fakedonald.clickfarming.domain.sales.SalesMan
import com.fakedonald.clickfarming.enums.StateTypeEnum
import com.fakedonald.clickfarming.enums.sales.SalesManPasswordTypeEnum
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import com.fakedonald.clickfarming.extension.*
import com.fakedonald.clickfarming.repository.UserBankCardRepository
import com.fakedonald.clickfarming.repository.merchant.MerchantRepository
import com.fakedonald.clickfarming.repository.merchant.MerchantShopRepository
import com.fakedonald.clickfarming.repository.sales.SalesManRepository
import com.fakedonald.clickfarming.security.TokenService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

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
    val passwordEncoder: BCryptPasswordEncoder,
) {


    /**
     * 获取当前登录用户
     */
    @GetMapping("/currentUser")
    suspend fun getCurrentUser() = tokenService.getSalesManUser().toJson()

    /**
     * 重置密码
     */
    @PostMapping("/updatePassword")
    suspend fun initialWithdrawPassword(@RequestBody request: SalesManResetPasswordRequest): Response {
        tokenService.resetSalesPassword(request)
        return Response.success()
    }

    /**
     * 查询我的业务员
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
     * 添加业务员
     */
    @PostMapping("/addSalesMan")
    fun addSalesMan(username: String): Response {
        val count = salesManRepository.count(SalesMan::username.equal(username))
        if (count != 0L) throw CustomException("用户名${username}已存在")
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
     *  备注业务员
     */
    @PutMapping("/updateSalesRemark")
    fun updateSalesRemark(@RequestBody request: UpdateRemarkRequest): Response {
        val username = tokenService.getClaim("username") as String
        val entity = salesManRepository.findById(request.id).notFound()
        if (username != entity.belongsToUser) return Response.error(message = "参数错误")
        entity.remark = request.remark
        return salesManRepository.save(entity).toJson()
    }

    /**
     * 切换业务员启用状态
     */
    @PutMapping("/toggleEnabled/{id}")
    fun toggleEnabled(@PathVariable id: Long): Response {
        val username = tokenService.getClaim("username") as String
        val entity = salesManRepository.findById(id).notFound()
        if (username != entity.belongsToUser) return Response.error(message = "参数错误")
        entity.enabled = !entity.enabled
        return salesManRepository.save(entity).toJson()
    }

    /**
     * 查询我的商家列表
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
     * 备注商家接口
     */
    @PutMapping("/updateMerchantRemark")
    fun updateMerchantRemark(@RequestBody request: UpdateRemarkRequest): Response {
        val salesManIdList = getSubSalesIdList()
        val entity = merchantRepository.findById(request.id).notFound()
        if (!salesManIdList.any { it == entity.salesManId }) throw CustomException("参数错误")
        entity.remark = request.remark
        return merchantRepository.save(entity).toJson()
    }

    /**
     * 获取店铺列表
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
     * 获取我的银行卡列表
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
     * 业务员绑定银行卡
     */
    @PostMapping("/addBankCard")
    fun addBankCard(@RequestBody request: UserBankCard): Response {
        request.state = StateTypeEnum.PASS
        request.userType = UserTypeEnum.SALES_MAN
        return userBankCardRepository.save(request).toJson()
    }

    // 私有函数
    /**
     * 获取所有下级
     *
     * @param username 查询条件
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

//备注请求
data class UpdateRemarkRequest(
    val id: Long,
    val remark: String,
)

// 店铺列表请求
data class ShopListRequest(
    // 商家用户名
    var merchantUsername: String?,
    // 业务员用户名
    var salesManUsername: String?,
    // 店铺名
    var shopName: String?,
)

/**
 * 修改密码请求体`
 */
data class SalesManResetPasswordRequest(
    // 密码类型
    val passwordType: SalesManPasswordTypeEnum,
    // 原密码
    val originalPassword: String?,
    // 新密码
    val newPassword: String,
    // 确认密码
    val confirmPassword: String,
)