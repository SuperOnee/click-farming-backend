package com.fakedonald.clickfarming.contorller.customer

import com.fakedonald.clickfarming.domain.customer.CollectAccount
import com.fakedonald.clickfarming.domain.customer.Identity
import com.fakedonald.clickfarming.enums.customer.IdentityStatusEnum
import com.fakedonald.clickfarming.extension.CustomException
import com.fakedonald.clickfarming.extension.Response
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.repository.customer.CustomerRepository
import com.fakedonald.clickfarming.security.TokenService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * 试客Controller
 */
@RestController
@RequestMapping("/customerApi")
class CustomerController(
        private val tokenService: TokenService,
        private val customerRepository: CustomerRepository,
) {

    @GetMapping("/currentUser")
    fun currentUser() = tokenService.getCustomerUser().toJson()

    /**
     * 验证身份信息
     */
    @PostMapping("/verify")
    fun verify(@RequestBody request: Identity): Response {
        val currentUser = tokenService.getCustomerUser()

        if (currentUser.identityStatus != IdentityStatusEnum.PENDING_SUBMIT ||
                currentUser.identityStatus != IdentityStatusEnum.FAIL) {
            throw CustomException("请勿重复提交")
        }

        currentUser.identity = request
        currentUser.identityStatus = IdentityStatusEnum.PENDING
        return customerRepository.save(currentUser).toJson()
    }

    /**
     * 绑定提现账号
     */
    @PutMapping("/collectAccount")
    fun collectAccount(@RequestBody request: CollectAccountRequest): Response {
        val currentUser = tokenService.getCustomerUser()
        val match = tokenService.match(request.withdrawPassword, currentUser.withdrawPassword)
        if (!match) throw CustomException("提现密码错误")
        currentUser.collectAccount = request.data
        return customerRepository.save(currentUser).toJson()
    }

    /**
     * 修改提现密码
     */
    @PutMapping("/updateWithdrawPassword")
    fun updateWithdrawPassword(@RequestBody request: UpdateWithdrawPasswordRequest): Response {
        if (request.newPassword != request.confirmPassword) throw CustomException("两次密码输入不一致")
        val currentUser = tokenService.getCustomerUser()
        val match = tokenService.match(request.withdrawPassword, currentUser.withdrawPassword)
        if (!match) throw CustomException("原密码错误")
        currentUser.withdrawPassword = tokenService.encodePassword(request.newPassword)
        return Response.success()
    }

}

/**
 * 修改提现密码请求
 */
data class UpdateWithdrawPasswordRequest(
        val withdrawPassword: String,
        val newPassword: String,
        val confirmPassword: String,
)

/**
 * 设置体现密码请求
 */
data class CollectAccountRequest(
        val data: CollectAccount,
        val withdrawPassword: String,
)