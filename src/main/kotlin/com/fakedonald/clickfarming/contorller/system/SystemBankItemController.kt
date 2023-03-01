package com.fakedonald.clickfarming.contorller.system

import com.fakedonald.clickfarming.domain.system.SystemBankItem
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.service.system.SystemBankItemService
import org.springframework.web.bind.annotation.*

/**
 * @author nathan
 * @date 2023/2/15 18:27
 *
 * 银行选项控制器
 */
@RestController
@RequestMapping("/systemBankItem")
class SystemBankItemController(val bankItemService: SystemBankItemService) {


    /**
     * 查询银行选项列表
     */
    @GetMapping
    suspend fun queryData() = bankItemService.queryList().toJson()

    /**
     * 保存银行选项
     */
    @PostMapping
    suspend fun save(@RequestBody bankItem: SystemBankItem) = bankItemService.saveEntity(bankItem).toJson()
}