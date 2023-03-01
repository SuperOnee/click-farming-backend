package com.fakedonald.clickfarming.domain

import jakarta.validation.constraints.NotEmpty
/**
 * 登录请求
 */
data class AuthRequest(
    // 用户名
    @NotEmpty
    val username: String,
    // 密码
    @NotEmpty
    val password: String,
    // 验证码
    @NotEmpty
    val code: String,
)