package com.fakedonald.clickfarming.contorller.system

import com.fakedonald.clickfarming.cache.*
import com.fakedonald.clickfarming.domain.system.SystemConfigTaskAdditionalPrice
import com.fakedonald.clickfarming.enums.system.ConfigTypeEnum
import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import com.fakedonald.clickfarming.extension.Response
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.extension.valueToEnum
import com.fakedonald.clickfarming.service.system.SystemConfigTaskAdditionalPriceService
import org.springframework.web.bind.annotation.*

/**
 * @author nathan
 * @date 2023/2/17 19:44
 *
 * 网站配置Controller
 */
@RestController
@RequestMapping("/systemConfig")
class SystemConfigController(
    val globalCache: GlobalCache,
    val configTaskAdditionalPriceService: SystemConfigTaskAdditionalPriceService
) {


    /**
     * 查询网站配置
     */
    @GetMapping("/{configType}")
    suspend fun queryData(@PathVariable configType: String): Response {
        val configTypeRes = enumValues<ConfigTypeEnum>().first { it.value == configType }
        return globalCache.get<String>(configTypeRes.key)?.toJson() ?: Response.success()
    }

    /**
     * 保存网站配置
     */
    @PostMapping("/{configType}")
    suspend fun save(@PathVariable configType: String, @RequestBody json: String): Response {
        val configTypeRes = enumValues<ConfigTypeEnum>().first { it.value == configType }
        globalCache.set(configTypeRes.key, json)
        return globalCache.get<String>(configTypeRes.key)?.toJson() ?: Response.error("Something went wrong!")
    }

    /**
     * 任务附加费配置
     */
    @GetMapping("/additionalPrice/{taskType}")
    suspend fun queryAdditionalPrice(@PathVariable taskType: String): Response {
        val taskTypeValue = valueToEnum<TaskTypeEnum>(taskType)
        return configTaskAdditionalPriceService.queryData(taskTypeValue)?.toJson() ?: Response.success()
    }

    /**
     * 任务附加费配置保存
     */
    @PostMapping("/additionalPrice")
    suspend fun saveAdditionalPriceConfig(@RequestBody configTaskAdditionalPrice: SystemConfigTaskAdditionalPrice) =
        configTaskAdditionalPriceService.saveEntity(configTaskAdditionalPrice).toJson()
}