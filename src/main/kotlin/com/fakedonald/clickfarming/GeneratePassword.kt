package com.fakedonald.clickfarming

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

/**
 * @author nathan
 * @date 2023/2/12 03:19
 */

fun main() {
    val passwordEncoder = BCryptPasswordEncoder()
    val encodePassword = passwordEncoder.encode("123456")
    println(encodePassword)
}