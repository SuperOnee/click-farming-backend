package com.fakedonald.clickfarming.contorller.handler

import com.fakedonald.clickfarming.extension.Response
import com.fakedonald.clickfarming.extension.CustomException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler
    fun serviceExceptionHandler(ex: CustomException) = Response.error(message = ex.message)

    @ExceptionHandler
    fun exceptionHandler(ex: Exception): Response {
        ex.printStackTrace()
        return Response.error(message = ex.message)
    }
}