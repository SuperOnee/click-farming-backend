package com.fakedonald.clickfarming.enums.merchant

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/25 19:42
 *
 * 商家店铺类型枚举
 */
enum class ShopTypeEnum(@get:JsonValue val value: String) {

    TAOBAO("淘宝"),

    JD("京东"),

    PDD("拼多多"),

    TIKTOK("抖音"),

    ALI("阿里巴巴")
}