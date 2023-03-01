package com.fakedonald.clickfarming.enums.system

import com.fakedonald.clickfarming.cache.*
import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/19 15:47
 *
 * 网站配置枚举
 */
enum class ConfigTypeEnum(@get:JsonValue val value: String, val key: String) {

    WEB_CONFIG("网站配置", WEB_CONFIG_CACHE_KEY),

    TASK_TIMEOUT("任务时间", TASK_TIMEOUT_CONFIG_CACHE_KEY),

    COMMISSION_ADDITIONAL_FEE("任务附加费", COMMISSION_ADDITIONAL_FEE_CACHE_KEY),

    GOOD_COMMENT_FEE("好评任务费", GOOD_COMMENT_FEE_CACHE_KEY),

    MERCHANT_RETURN("商返设置", MERCHANT_RETURN_CACHE_KEY),
}