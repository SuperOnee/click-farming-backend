package com.fakedonald.clickfarming.enums.sales

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/16 00:06
 *
 * 主题风格
 */
enum class ThemeTypeEnum(@get:JsonValue val value: String) {

    DEFAULT("默认"),

    BLUE("蓝色"),

    BOTTOM_BLUE("底蓝色"),

    GREEN("绿色"),

    ORANGE("橙色"),

    PURPLE("紫色"),

    BLACK("黑色"),

    RED("红色");
}