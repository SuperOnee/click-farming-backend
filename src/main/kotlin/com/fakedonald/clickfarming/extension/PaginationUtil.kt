package com.fakedonald.clickfarming.extension

import org.springframework.data.domain.PageRequest


data class PageDomain(val page: Int? = 1, val size: Int? = 10)

/**
 * 根据前端参数获取分页参数
 */
private fun getPageRequest(): PageDomain {
    val page = ServletUtil.getParameter("page")?.toInt()
    val size = ServletUtil.getParameter("size")?.toInt()
    return PageDomain(page, size)
}

/**
 * 生成分页请求
 */
fun generatePageRequest(): PageRequest {
    val pageDomain = getPageRequest()
    return PageRequest.of((pageDomain.page ?: 1).pageCalculation(), pageDomain.size ?: 10)
}