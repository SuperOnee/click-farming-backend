package com.fakedonald.clickfarming.contorller.system

import com.fakedonald.clickfarming.domain.sales.SalesManSubSiteConfig
import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import com.fakedonald.clickfarming.extension.Response
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.service.system.SalesManSubSiteConfigService
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

/**
 * @author nathan
 * @date 2023/2/15 22:33
 *
 * 分站定价Controller
 */
@RestController
@RequestMapping("/salesManSubSiteConfig")
class SalesManSubSiteConfigController(val subSiteConfigService: SalesManSubSiteConfigService) {


    /**
     * 查询分站定价配置
     */
    @GetMapping("/{subSiteId}")
    suspend fun queryData(@PathVariable subSiteId: Long) = subSiteConfigService.queryData(subSiteId).toJson()

    /**
     * 分站定价配置保存接口
     */
    @PostMapping
    suspend fun save(@RequestBody configList: List<SalesManSubSiteConfig>): Response {
        subSiteConfigService.saveEntity(configList)
        return Response.success()
    }
}

// 返回的Config元素
data class ConfigItem(
    // 任务类型
    val taskType: TaskTypeEnum,
    val configList: List<WrappedConfig>
)

/**
 * 包装data class
 */
data class WrappedConfig(
    // 价格区间
    var startPrice: BigDecimal,
    var endPrice: BigDecimal,
    // 立返服务费(参考)
    var instantServiceFee: BigDecimal,
    //立返服务费
    var instantServiceFeeInput: BigDecimal? = BigDecimal.ZERO,
)