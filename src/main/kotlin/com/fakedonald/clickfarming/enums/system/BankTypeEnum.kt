package com.fakedonald.clickfarming.enums.system

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/15 18:09
 *
 * 银行类型枚举
 */
enum class BankTypeEnum(@get:JsonValue val value: String) {

    BANK("银行卡"),

    ALIPAY("支付宝"),

    WECHAT("微信");
}