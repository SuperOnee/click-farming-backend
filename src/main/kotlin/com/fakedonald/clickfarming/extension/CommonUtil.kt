package com.fakedonald.clickfarming.extension

import cn.hutool.captcha.CaptchaUtil

/**
 * @author nathan
 * @date 2023/2/10 01:56
 *
 * 工具类
 */

fun entityNotFound(): Nothing = throw CustomException("数据不存在")

val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

/**
 * 生成随机代码
 *
 * @param length 长度
 */
fun generateRandomCode(length: Int): String {
    var res: String = ""
    for (i in 1..length) {
        val rand = (Math.random() * charPool.size).toInt()
        val c = charPool[rand]
        res += c
    }

    return res.uppercase()
}













