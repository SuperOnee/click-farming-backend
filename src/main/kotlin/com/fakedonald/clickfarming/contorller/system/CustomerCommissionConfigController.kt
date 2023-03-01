package com.fakedonald.clickfarming.contorller.system

import com.fakedonald.clickfarming.domain.sales.CustomerCommissionConfig
import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import com.fakedonald.clickfarming.extension.Response
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.extension.valueToEnum
import com.fakedonald.clickfarming.service.system.CustomerCommissionConfigService
import org.springframework.web.bind.annotation.*

/**
 * @author nathan
 * @date 2023/2/15 21:21
 *
 * 试客佣金Controller
 */
@RestController
@RequestMapping("/customerCommissionConfig")
class CustomerCommissionConfigController(val commissionConfigService: CustomerCommissionConfigService) {


    /**
     * 根据任务类型获取配置
     */
    @GetMapping("/{type}")
    suspend fun queryData(@PathVariable type: String): Response {
        val taskType = valueToEnum<TaskTypeEnum>(type)
        return commissionConfigService.queryByType(taskType).toJson()
    }

    /**
     * 更新任务定价配置
     */
    @PostMapping
    suspend fun save(@RequestBody config: CustomerCommissionConfig) =
        commissionConfigService.saveEntity(config).toJson()
}