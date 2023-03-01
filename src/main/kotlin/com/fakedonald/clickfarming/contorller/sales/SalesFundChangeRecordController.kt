package com.fakedonald.clickfarming.contorller.sales

import com.fakedonald.clickfarming.contorller.system.FundChangeRecordQueryRequest
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import com.fakedonald.clickfarming.extension.Response
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.security.TokenService
import com.fakedonald.clickfarming.service.system.FundChangeRecordService
import org.springframework.web.bind.annotation.*

/**
 * @author nathan
 * @date 2023/2/23 23:13
 */
@RestController
@RequestMapping("/sales/fundChangeRecord")
class SalesFundChangeRecordController(
    val fundChangeRecordService: FundChangeRecordService,
    val tokenService: TokenService
) {


    /**
     * 业务员查询资金明细
     */
    @GetMapping
    suspend fun queryData(): Response {
        val userId = tokenService.getClaim("userId") as Long
        return fundChangeRecordService.queryPage(FundChangeRecordQueryRequest(userId = userId, userType = UserTypeEnum.SALES_MAN)).toJson()
    }
}