package com.fakedonald.clickfarming.extension

import java.io.Serializable

// 返回的Json格式
data class Response(val status: Boolean, val data: Any?, val message: String?) : Serializable {
    companion object {
        fun success(data: Any? = null, message: String? = "success") = Response(true, data, message)

        fun success() = success(message = "success")

        fun error(data: Any? = null, message: String? = "error") = Response(false, data, message)
    }
}