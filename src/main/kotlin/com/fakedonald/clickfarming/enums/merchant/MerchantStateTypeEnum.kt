package com.fakedonald.clickfarming.enums.merchant

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/25 19:56
 *
 * 商家状态枚举
 */
enum class MerchantStateTypeEnum(@get: JsonValue val value: String) {

    PENDING("待审核"),

    PASS("审核通过"),

    FAIL("审核未通过"),

    LOCKED("账号锁定")
}