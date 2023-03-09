package com.fakedonald.clickfarming.enums.sales

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/23 23:43
 *
 * 通用密码类型枚举类
 */
enum class CommonPasswordTypeEnum(@get:JsonValue val value: String) {

    // 登录密码
    LOGIN_PASSWORD("登录密码"),

    // 提现密码
    WITHDRAW_PASSWORD("提现密码")

}