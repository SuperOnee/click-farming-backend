package com.fakedonald.clickfarming.extension

/**
 * @author nathan
 *
 * 自定义异常
 */
class CustomException(private val msg: String) : Exception() {
    override val message: String
        get() = msg
}