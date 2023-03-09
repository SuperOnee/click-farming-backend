package com.fakedonald.clickfarming.contorller.security

import cn.hutool.captcha.CaptchaUtil
import com.fakedonald.clickfarming.cache.GlobalCache
import com.fakedonald.clickfarming.domain.AuthRequest
import com.fakedonald.clickfarming.domain.merchant.Merchant
import com.fakedonald.clickfarming.domain.sales.SalesMan
import com.fakedonald.clickfarming.extension.*
import com.fakedonald.clickfarming.repository.merchant.MerchantRepository
import com.fakedonald.clickfarming.repository.sales.SalesManRepository
import com.fakedonald.clickfarming.security.CustomerDetailsServiceImpl
import com.fakedonald.clickfarming.security.MerchantDetailsServiceImpl
import com.fakedonald.clickfarming.security.SalesManDetailsServiceImpl
import com.fakedonald.clickfarming.security.SystemUserDetailsServiceImpl
import com.github.benmanes.caffeine.cache.Cache
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

/**
 * @author nathan
 * @date 2023/2/12 01:26
 */
@RestController
@RequestMapping("/auth")
class AuthController(
    val cache: Cache<Any, Any>,
    val adminDetailsService: SystemUserDetailsServiceImpl,
    val salesManDetailsService: SalesManDetailsServiceImpl,
    val merchantDetailsService: MerchantDetailsServiceImpl,
    val customerDetailsService: CustomerDetailsServiceImpl,
    val salesManRepository: SalesManRepository,
    val merchantRepository: MerchantRepository,
    val passwordEncoder: BCryptPasswordEncoder,
    val globalCache: GlobalCache
) {
    @GetMapping
    suspend fun getCaptcha(): Response {
        val lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100, 4, 50)
        val code = lineCaptcha.code
        println(code)
        cache.put(code, true)
        val imageBase64 = lineCaptcha.imageBase64
        return imageBase64.toJson()
    }

    /**
     * 管理登录接口
     */
    @PostMapping("/admin")
    suspend fun adminAuth(@RequestBody request: AuthRequest): Response {
        cache.getIfPresent(request.code)?.let {
            cache.asMap().remove(request.code)
            return adminDetailsService.authenticateAdmin(request).toJson()
        } ?: throw CustomException("验证码错误或已过期")
    }

    /**
     * 业务员登录接口
     */
    @PostMapping("/salesMan")
    suspend fun salesManAuth(@RequestBody request: AuthRequest): Response {
        cache.getIfPresent(request.code)?.let {
            return salesManDetailsService.authenticate(request).toJson()
        } ?: throw CustomException("验证码错误或已过期")
    }

    /**
     * 商家注册
     */
    @PostMapping("/merchantRegister")
    fun merchantRegister(@RequestBody request: MerchantRegisterRequest): Response {
        if (request.password != request.confirmPassword)
            throw CustomException("两次密码输入不一致")
        val merchantUsernameCount = merchantRepository.count(Merchant::username.equal(request.username))
        if (merchantUsernameCount != 0L) return Response.error(message = "用户名${request.username}已存在")
        cache.getIfPresent(request.code)?.let {
            val salesMan = salesManRepository.findOne(SalesMan::shareCode.equal(request.shareCode)).notFound()
            val newMerchant = Merchant(
                salesManId = salesMan.id, username = request.username,
                password = passwordEncoder.encode(request.password), wechat = request.wechat, qq = request.qq,
                withdrawPassword = passwordEncoder.encode("123456"),
                defaultWithdrawPassword = true,
            )
            return merchantRepository.save(newMerchant).toJson()
        } ?: throw CustomException("验证码错误或已过期")
    }

    /**
     * 商家登录
     */
    @PostMapping("/merchantLogin")
    fun merchantLogin(@RequestBody request: AuthRequest): Response {
        cache.getIfPresent(request.code)?.let {
            return merchantDetailsService.authenticate(request).toJson()
        } ?: throw CustomException("验证码错误或已过期")
    }

    /**
     * 试客登录
     */
    @PostMapping("/customerLogin")
    fun customerLogin(@RequestBody request: AuthRequest) : Response {
        cache.getIfPresent(request.code)?.let {
            return customerDetailsService.authenticate(request).toJson()
        } ?: throw CustomException("验证码错误或已过期")
    }
}

data class MerchantRegisterRequest(
    // 用户名(手机号)
    val username: String,
    // 密码
    val password: String,
    // 确认密码
    val confirmPassword: String,
    val wechat: String,
    val qq: String,
    // 验证码
    val code: String,
    // 业务员邀请码
    val shareCode: String,
)










