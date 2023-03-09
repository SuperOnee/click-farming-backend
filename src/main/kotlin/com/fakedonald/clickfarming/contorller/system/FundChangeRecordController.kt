package com.fakedonald.clickfarming.contorller.system

import com.fakedonald.clickfarming.enums.sales.TradeTypeEnum
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.service.system.FundChangeRecordService
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime

/**
 * @author nathan
 * @date 2023/2/15 14:06
 */
@RestController
@RequestMapping("/fundChangeRecord")
class FundChangeRecordController(val fundChangeRecordService: FundChangeRecordService) {


    @GetMapping
    suspend fun queryData(queryRequest: FundChangeRecordQueryRequest) = fundChangeRecordService.queryPage(queryRequest).toJson()
}

/**
 * 请求参数
 */
data class FundChangeRecordQueryRequest(
    // 用户ID
    var userId: Long?,
    // 用户类型
    var userType: UserTypeEnum?,
    // 订单编号
    val orderNo: String? = null,
    // 店铺
    val shopName: String? = null,
    // 任务编号
    val taskNo: String? = null,
    // 操作人
    val operatedUser: String? = null,
    // 交易类型
    val tradeType: TradeTypeEnum? = null,
    // 开始时间
    val startAt: LocalDateTime? = null,
    // 结束时间
    val endAt: LocalDateTime? = null,
)