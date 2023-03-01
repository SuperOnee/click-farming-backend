package com.fakedonald.clickfarming.enums

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/25 22:24
 *
 * 状态枚举
 */
enum class StateTypeEnum(@get: JsonValue val value: String) {

    PENDING("审核中"),

    PASS("审核通过"),

    FAIL("审核未过")
}