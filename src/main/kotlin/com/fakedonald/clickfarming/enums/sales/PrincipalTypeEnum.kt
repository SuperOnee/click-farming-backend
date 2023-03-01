package com.fakedonald.clickfarming.enums.sales

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/15 23:33
 *
 * 本金返款类型
 */
enum class PrincipalTypeEnum(@get:JsonValue val value: String) {

    DEFAULT("默认设置"),

    PLATFORM("平台"),

    MERCHANT("商家"),

    MERCHANT_SELECT("商家自选");
}