package com.fakedonald.clickfarming.enums.sales

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/16 00:22
 */
enum class PrincipalServiceFeeTypeEnum(@get:JsonValue val value: String) {

    DEFAULT("默认设置(0元)"),

    PERCENTAGE("按百分比"),

    FIXED_AMOUNT("固定金额");
}