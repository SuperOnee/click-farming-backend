package com.fakedonald.clickfarming.enums.sales

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/14 17:59
 *
 * 分享权限
 */
enum class SharePermissionEnum(@get:JsonValue val value: String) {
    // 关闭
    CLOSED("关闭"),

    // 不限
    NO_LIMIT("不限"),

    // 商家
    BUSINESS("商家"),

    // 试客
    CUSTOMER("试客");

}