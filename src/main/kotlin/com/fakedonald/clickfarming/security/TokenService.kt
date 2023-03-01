package com.fakedonald.clickfarming.security

import com.fakedonald.clickfarming.contorller.sales.SalesManResetPasswordRequest
import com.fakedonald.clickfarming.domain.sales.SalesMan
import com.fakedonald.clickfarming.domain.system.SystemUser
import com.fakedonald.clickfarming.enums.sales.SalesManPasswordTypeEnum
import com.fakedonald.clickfarming.extension.CustomException
import com.fakedonald.clickfarming.extension.notFound
import com.fakedonald.clickfarming.repository.sales.SalesManRepository
import com.fakedonald.clickfarming.repository.system.SystemUserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.stereotype.Component

/**
 * @author nathan
 * @date 2023/2/12 06:15
 */
@Component
class TokenService(
    val systemUserRepository: SystemUserRepository,
    val salesManRepository: SalesManRepository,
    val passwordEncoder: BCryptPasswordEncoder,
) {

    /**
     * 获取当前Authentication
     *
     * @return authentication
     */
    fun getAuthentication(): Authentication {
        val context = SecurityContextHolder.getContext()
        return context.authentication
    }

    /**
     * 获取Admin用户
     */
    fun getAdminUser(): SystemUser = systemUserRepository.findById(getClaim("userId") as Long).notFound()

    /**
     * 获取业务员用户
     */
    fun getSalesManUser(): SalesMan = salesManRepository.findById(getClaim("userId") as Long).notFound()

    /**
     * 校验管理密码是否合法
     */
    fun validateAdminPassword(
        adminUser: SystemUser,
        sourcePassword: String,
        adminPasswordType: AdminPasswordTypeEnum
    ): Boolean {
        return when (adminPasswordType) {
            AdminPasswordTypeEnum.LOGIN_PASSWORD -> passwordEncoder.matches(
                sourcePassword,
                adminUser.password,
            )

            AdminPasswordTypeEnum.FUND_OPERATION -> passwordEncoder.matches(
                sourcePassword, adminUser.fundOperatePassword
            )

            AdminPasswordTypeEnum.TRANSFER -> passwordEncoder.matches(
                sourcePassword,
                adminUser.transferPassword,
            )
        }
    }

    /**
     * 重置密码
     */
    suspend fun resetAdminPassword(request: ResetAdminPasswordRequest) {
        val adminUser = withContext(Dispatchers.IO) {
            systemUserRepository.findById(request.id).notFound()
        }
        val valid = validateAdminPassword(adminUser, request.password, request.passwordType)
        if (valid) {
            when (request.passwordType) {
                AdminPasswordTypeEnum.LOGIN_PASSWORD -> adminUser.password = passwordEncoder.encode(request.newPassword)

                AdminPasswordTypeEnum.FUND_OPERATION -> adminUser.fundOperatePassword =
                    passwordEncoder.encode(request.newPassword)

                AdminPasswordTypeEnum.TRANSFER -> adminUser.transferPassword = passwordEncoder.encode(request.newPassword)
            }

            // update password
            withContext(Dispatchers.IO) {
                systemUserRepository.save(adminUser)
            }
        } else throw CustomException("密码错误")
    }

    /**
     * 重置业务员密码, 提现密码 / 登录密码
     */
    suspend fun resetSalesPassword(request: SalesManResetPasswordRequest) {
        // 参数校验
        when (request.passwordType) {
            // 提现密码
            SalesManPasswordTypeEnum.WITHDRAW_PASSWORD -> {
                if (request.newPassword == request.confirmPassword) {
                    val entity = getSalesManUser()
                    entity.withdrawPassword = passwordEncoder.encode(request.newPassword)
                    entity.defaultWithdrawPassword = true
                    withContext(Dispatchers.IO) {
                        salesManRepository.save(entity)
                    }
                } else {
                    throw CustomException("两次密码输入不一致")
                }
            }
            // 登录密码
            SalesManPasswordTypeEnum.LOGIN_PASSWORD -> {
                if (request.newPassword != request.confirmPassword) throw CustomException("两次密码输入不一致")
                val entity = getSalesManUser()
                if (!passwordEncoder.matches(request.originalPassword, entity.password))
                    throw CustomException("原密码错误")
                entity.password = passwordEncoder.encode(request.newPassword)
                withContext(Dispatchers.IO) {
                    salesManRepository.save(entity)
                }
            }
        }
    }

    /**
     * 获取对应的claim
     */
    fun getClaim(key: String): Any {
        val authentication = getAuthentication()
        val principal = authentication.principal
        val jwt = principal as Jwt
        return jwt.claims[key] ?: ""
    }
}

// 重置管理员密码请求
data class ResetAdminPasswordRequest(
    val id: Long,
    val passwordType: AdminPasswordTypeEnum,
    val password: String,
    val newPassword: String,
)

// 操作密码枚举
enum class AdminPasswordTypeEnum {
    // 登录密码
    LOGIN_PASSWORD,

    // 资金管理密码
    FUND_OPERATION,

    // 转账密码
    TRANSFER
}










