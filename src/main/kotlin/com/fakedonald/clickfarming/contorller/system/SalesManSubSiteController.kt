package com.fakedonald.clickfarming.contorller.system

import com.fakedonald.clickfarming.domain.sales.SalesManSubSite
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.service.system.SalesManSubSiteService
import org.springframework.web.bind.annotation.*

/**
 * @author nathan
 * @date 2023/2/17 19:06
 */
@RestController
@RequestMapping("/salesManSubSite")
class SalesManSubSiteController(val subSiteService: SalesManSubSiteService) {


    /**
     * 根据ID查询分站数据
     */
    @GetMapping("/{subSiteId}")
    suspend fun queryData(@PathVariable subSiteId: Long) = subSiteService.queryById(subSiteId).toJson()

    /**
     * 保存银行配置
     */
    @PostMapping("/bankConfig")
    suspend fun saveBankConfig(@RequestBody subSite: SalesManSubSite) = subSiteService.saveBankConfig(subSite).toJson()

    /**
     * 保存分站配置
     */
    @PostMapping("/config")
    suspend fun saveConfig(@RequestBody subSite: SalesManSubSite) =
        subSiteService.saveConfigData(subSite).toJson()
}