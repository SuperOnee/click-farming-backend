package com.fakedonald.clickfarming.enums.sales

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/12 16:33
 *
 * 用户类型
 */
enum class UserTypeEnum(@get:JsonValue val value: String) {
    ADMIN("管理员"),

    MERCHANT("商家"),

    SALES_MAN("业务员"),

    CUSTOMER("试客"),
}