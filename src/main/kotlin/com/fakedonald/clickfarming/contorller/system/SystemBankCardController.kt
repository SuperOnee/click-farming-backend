package com.fakedonald.clickfarming.contorller.system

import com.fakedonald.clickfarming.domain.system.SystemBankCard
import com.fakedonald.clickfarming.extension.Response
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.service.system.SystemBankCardService
import org.springframework.web.bind.annotation.*

/**
 * @author nathan
 * @date 2023/2/15 18:33
 *
 * 银行卡Controller
 */
@RestController
@RequestMapping("/systemBankCard")
class SystemBankCardController(val bankCardService: SystemBankCardService) {


    /**
     * 银行卡分页数据
     */
    @GetMapping
    suspend fun queryData(params: BankCardQueryParams) = bankCardService.queryPage(params).toJson()

    /**
     * 银行卡列表
     */
    @GetMapping("/list")
    suspend fun queryList() = bankCardService.queryList().toJson()

    /**
     * 新增/编辑银行卡
     */
    @PostMapping
    suspend fun save(@RequestBody bankCard: SystemBankCard) = bankCardService.saveEntity(bankCard).toJson()

    /**
     * 根据ID删除银行卡
     */
    @DeleteMapping("/{id}")
    suspend fun delete(@PathVariable("id") id: Long): Response {
        bankCardService.deleteEntity(id)
        return Response.success()
    }
}

data class BankCardQueryParams(
    // 账号
    val account: String?,

    // 户名
    val realName: String?
)







