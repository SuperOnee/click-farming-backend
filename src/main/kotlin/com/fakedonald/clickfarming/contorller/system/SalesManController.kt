package com.fakedonald.clickfarming.contorller.system

import com.fakedonald.clickfarming.enums.sales.SharePermissionEnum
import com.fakedonald.clickfarming.extension.Response
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.service.system.SalesManService
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Pattern
import org.springframework.web.bind.annotation.*
import java.math.BigDecimal

/**
 * @author nathan
 * @date 2023/2/10 01:23
 */

@RestController
@RequestMapping("/salesMan")
class SalesManController(val salesManService: SalesManService) {

    /**
     * 查询列表, 分页
     */
    @GetMapping
    suspend fun queryPage(salesManQueryParams: SalesManQueryParams) =
        salesManService.queryPage(salesManQueryParams).toJson()

    /**
     * 新增, 设置用户
     */
    @PostMapping
    suspend fun save(@RequestBody request: SaveRequest) = salesManService.saveEntity(request).toJson()

    /**
     * 重置密码
     */
    @PutMapping("/resetPassword/{id}")
    suspend fun resetPassword(@PathVariable("id") id: Long) = salesManService.resetPassword(id).toJson()

    /**
     * 切换状态
     */
    @PutMapping("/toggleEnable/{id}")
    suspend fun toggleUserEnable(@PathVariable("id") id: Long) = salesManService.toggleEntityEnable(id).toJson()

    /**
     * 刷新邀请码
     */
    @PutMapping("/refreshShareCode/{id}")
    suspend fun refreshShareCode(@PathVariable("id") id: Long) = salesManService.refreshEntityShareCode(id).toJson()

    /**
     * 业务员抽佣配置更新
     */
    @PutMapping("/commissionConfig/{id}")
    suspend fun commissionConfig(@PathVariable("id") id: Long, @RequestBody request: CommissionConfigUpdateRequest) =
        salesManService.updateCommissionConfig(id, request).toJson()

    /**
     * 调整业务员资金
     */
    @PutMapping("/alterFunds")
    suspend fun alterFunds(@RequestBody request: AlterFundRequest): Response {
        return salesManService.updateFund(request).toJson()
    }

    /**
     * 开启分站
     */
    @PostMapping("/openSubSite/{userId}")
    suspend fun openSubSite(@PathVariable userId: Long): Response {
        salesManService.openSubSite(userId)
        return Response.success()
    }
}

/**
 * 列表请求参数
 */
data class SalesManQueryParams(
    // ID
    val id: Long?,
    // 业务员
    val username: String?,
    // 上级业务员
    val belongsToUsername: String?,
    // 业务员类型
    val salesType: String?,
    // 分站
    val subSite: String?,
)

// 调整资金请求
data class AlterFundRequest(
    @NotNull
    val id: Long,
    // 操作
    @NotEmpty
    @Pattern(regexp = "[0-9()+\\-.]")
    val operation: String,
    // 备注
    @NotEmpty
    val remark: String?,
    // 调整资金密码
    @NotEmpty
    val fundOperatePassword: String
)

// 保存请求
data class SaveRequest(
    var id: Long?,
    // 用户名
    @NotEmpty
    var username: String,
    // 分享权限
    var sharePermission: SharePermissionEnum,
    // 商家业务抽佣
    var businessRate: BigDecimal,
    // 试客业务抽佣
    var customerRate: BigDecimal,
)

// 更新抽佣配置请求
data class CommissionConfigUpdateRequest(
    val businessConfig: List<BigDecimal>?,
    val customerConfig: List<BigDecimal>?
)
















