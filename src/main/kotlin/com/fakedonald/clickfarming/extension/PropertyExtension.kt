package com.fakedonald.clickfarming.extension

import java.util.*
import kotlin.reflect.full.declaredFunctions
import kotlin.reflect.full.memberProperties

/**
 * Optional为空抛出异常
 */
fun <T : Any> Optional<T>.notFound(): T {
    if (this.isPresent) return get()
    else throw CustomException("数据不存在")
}

fun <T : Any> Optional<T>.userNotFound(): T {
    if (this.isPresent) return get()
    else throw CustomException("用户不存在")
}

/**
 * 将任意数据转换为返回类型JsonResult
 */
fun Any.toJson(): Response = Response.success(this)

fun Int.pageCalculation(): Int = if (this <= 1) 0 else this - 1

/**
 * 将String值转换为Enum
 */
@Suppress("UNCHECKED_CAST")
inline fun <reified T : Enum<T>> valueToEnum(value: String): T {
    val targetEnum = T::class
    val enumValues = targetEnum.declaredFunctions.find { it.name == "values" }?.call()
            as Array<T>
    enumValues.forEach { enum ->
        if (enum::class.memberProperties.first().call(enum) == value) return enum
    }

    throw CustomException("参数错误")
}