package com.fakedonald.clickfarming.extension

import jakarta.servlet.http.HttpServletRequest
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes

class ServletUtil {

    companion object {

        fun getParameter(name: String) = getRequest()?.getParameter(name)

        /**
         * 获取Request
         */
        private fun getRequest(): HttpServletRequest? = getRequestAttributes()?.request

        /**
         * 获取Request参数
         */
        private fun getRequestAttributes(): ServletRequestAttributes? =
            RequestContextHolder.getRequestAttributes() as ServletRequestAttributes?
    }
}