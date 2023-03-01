package com.fakedonald.clickfarming.enums.system

import com.fasterxml.jackson.annotation.JsonValue
import jakarta.persistence.AttributeConverter
import jakarta.persistence.Converter
import java.lang.IllegalArgumentException

/**
 * @author nathan
 * @date 2023/2/15 19:16
 *
 * 任务类型枚举
 */
enum class TaskTypeEnum(@get:JsonValue val value: String) {
    TAOBAO_TASK("手机淘宝任务"),

    TAOBAO_PASS("手机淘宝淘口令"),

    TAOBAO_NO_SCREEN("手机淘宝无截图版"),

    TAOBAO_BLACK_SEARCH("手机淘宝黑搜模式"),

    TAOBAO_PRECISE_TAG("手淘搜索精准打标签"),

    TAOBAO_NEXT_DAY_TASK("手淘隔天任务"),

    TAOBAO_MAY_LIKE("手淘首页猜你喜欢"),

    TAOBAO_PREVIEW("手淘预览单"),

    JING_DONG("手机京东"),

    JING_XI("京喜"),

    PDD("手机拼多多"),

    TIKTOK_SHOP("抖音小店任务"),

    INTERNATION_TAOBAO("洋淘"),

    TAOBAO_TIKTOK_TASK_ENTRY("手机淘宝入口任务"),

    TAOBAO_STREAM("淘宝直播任务"),

    PDD_QR_CODE("拼拼多多二维码任务"),

    PDD_MULTI_LINK("拼多多链接任务"),

    TAOBAO_QR_CODE("淘宝二维码任务"),

    TAOBAO_PASS_SPREAD("手机淘宝淘口令裂变"),

    TAOBAO_DISCOUNT("手淘聚划算"),

    ALIBABA("手机阿里巴巴任务"),

    PDD_MULTI("拼多多批发模式"),

    PDD_BUSINESS("拼多多商家版任务"),

    TAOBAO_BUDGET("淘宝特价版");
}