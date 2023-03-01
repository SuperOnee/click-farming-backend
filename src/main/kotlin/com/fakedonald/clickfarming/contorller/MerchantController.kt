package com.fakedonald.clickfarming.contorller

import com.fakedonald.clickfarming.domain.common.UserBankCard
import com.fakedonald.clickfarming.domain.merchant.MerchantShop
import com.fakedonald.clickfarming.enums.StateTypeEnum
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import com.fakedonald.clickfarming.extension.Response
import com.fakedonald.clickfarming.extension.and
import com.fakedonald.clickfarming.extension.equal
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.repository.UserBankCardRepository
import com.fakedonald.clickfarming.repository.merchant.MerchantShopRepository
import com.fakedonald.clickfarming.security.TokenService
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
    val merchantShopRepository: MerchantShopRepository,
    val userBankCardRepository: UserBankCardRepository,
    val tokenService: TokenService,
) {


    /**
     * 获取店铺列表
     *
     * @param shopType 店铺类型
     */
    @GetMapping("/getMyShopList")
    fun queryData(shopType: String): Response {
        val userId = tokenService.getClaim("userId") as Long
        return merchantShopRepository.findAll(
            MerchantShop::merchantId.equal(userId)
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

    // 添加银行卡
    @PostMapping("/addBankCard")
    fun addBankCard(@RequestBody request: UserBankCard): Response {
        val userId = tokenService.getClaim("userId") as Long
        request.userId = userId
        request.userType = UserTypeEnum.MERCHANT
        request.state = StateTypeEnum.PENDING
        return userBankCardRepository.save(request).toJson()
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
}




















