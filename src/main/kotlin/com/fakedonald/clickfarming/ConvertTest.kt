package com.fakedonald.clickfarming

import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.memberProperties

/**
 * @author nathan
 * @date 2023/2/16 18:52
 */

fun main() {
    val str = "123"

    val enum = enumValueOf<TaskTypeEnum>(str)

    print(enum)

}
