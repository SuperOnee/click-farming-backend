package com.fakedonald.clickfarming.enums.sales

import com.fasterxml.jackson.annotation.JsonValue

/**
 * @author nathan
 * @date 2023/2/12 16:42
 *
 * 交易类型
 *
 */
enum class TradeTypeEnum(@get:JsonValue val value: String) {
    TOP_UP("账户充值"),

    WITHDRAW("账户提现"),

    PUBLISH_TASK("发布任务"),

    UPGRADE("升级续费"),

    ORDER_SETTLE("订单结算"),

    DIRECT_RETURN("直接返利"),

    INDIRECT_RETURN("间接返利"),

    TASK_SETTLE("任务结算"),

    CHANGE_MINUS("调整减少"),

    CHANGE_PLUS("调整增加"),

    SYSTEM_RED_POCKET("系统红包"),

    TOP_UP_RATE("充值点数"),

    APPEND_COMMENT("发起追评"),

    APPEND_COMMENT_SETTLE("追评结算"),

    ORDER_PAYMENT("订单付款"),

    PRINCIPAL_RETURN("本金返还"),

    COMMISSION_REWARD("佣金奖励"),

    UP_LEVEL_SETTLE("上级结算"),

    MANUALLY_SETTLE("手动结算"),

    ORDER_PRICE_CHANGE("订单改价"),

    TOP_UP_COMMISSION("充值佣金"),

    VALUE_ADDED_SERVICE("增值服务"),

    ADD_GOOD_COMMENT("添加好评"),

    AFTER_COMMENT_SETTLE("后置评价结算"),

    RECOMMEND_REWARD("分站介绍人抽佣"),

    OTHER("其他"),
}








