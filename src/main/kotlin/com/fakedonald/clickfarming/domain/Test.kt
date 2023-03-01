package com.fakedonald.clickfarming.domain

import com.fakedonald.clickfarming.enums.system.TaskTypeEnum

/**
 * @author nathan
 * @date 2023/2/15 20:34
 */

fun main() {
    val values = TaskTypeEnum.values().map { it.value }

    println(values)
}