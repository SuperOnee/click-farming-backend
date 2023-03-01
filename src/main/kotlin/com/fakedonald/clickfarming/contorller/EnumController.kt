package com.fakedonald.clickfarming.contorller

import com.fakedonald.clickfarming.enums.sales.TradeTypeEnum
import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import com.fakedonald.clickfarming.extension.toJson
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author nathan
 * @date 2023/2/15 22:09
 *
 * 枚举列表Controller
 */

@RestController
@RequestMapping("/enums")
class EnumController {

    /**
     * 任务类型列表
     */
    @GetMapping("/taskType")
    suspend fun queryTaskTypeList() = TaskTypeEnum.values().map { it.value }.toJson()

    /**
     * 交易类型列表
     */
    @GetMapping("/tradeType")
    suspend fun queryTradeTypeList() = TradeTypeEnum.values().map { it.value }.toJson()
}