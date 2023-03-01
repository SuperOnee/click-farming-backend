package com.fakedonald.clickfarming.enums.sales

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/23 23:43
 *
 * 业务员密码类型枚举
 */
enum class SalesManPasswordTypeEnum(@get:JsonValue val value: String) {

    // 登录密码
    LOGIN_PASSWORD("登录密码"),

    // 提现密码
    WITHDRAW_PASSWORD("提现密码")

}