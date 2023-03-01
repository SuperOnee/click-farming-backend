package com.fakedonald.clickfarming.enums.sales

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/17 20:10
 *
 * 业务员类型枚举
 */
enum class SalesManTypeEnum(@get:JsonValue val value: String) {

    ALL("全部"),

    MAIN_PLATFORM("总站下业务员"),

    SUB_PLATFORM("分站下业务员"),

    SUB("分站")
}