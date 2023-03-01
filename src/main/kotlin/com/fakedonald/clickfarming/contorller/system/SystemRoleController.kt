package com.fakedonald.clickfarming.contorller.system

import com.fakedonald.clickfarming.domain.system.SystemRole
import com.fakedonald.clickfarming.extension.Response
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.service.system.SystemRoleService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*

/**
 * @author nathan
 * @date 2023/2/9 07:44
 */
@RestController
@RequestMapping("/systemRole")
//@PreAuthorize("hasRole('admin') && hasAuthority('权限管理')")
class SystemRoleController(val roleService: SystemRoleService) {

    @GetMapping
    suspend fun list(enabled: Boolean?) = roleService.queryList(enabled).toJson()

    /**
     * 根据ID查询
     */
    @GetMapping("/{id}")
    suspend fun query(@PathVariable id: Long) = roleService.queryById(id).toJson()

    /**
     * 保存角色
     */
    @PostMapping
    suspend fun save(@Valid @RequestBody role: SystemRole) = roleService.saveEntity(role).toJson()

    /**
     * 根据ID删除用户
     */
    @DeleteMapping("/{id}")
    suspend fun deleteById(@PathVariable("id") id: Long?): Response {
        roleService.deleteById(id)
        return Response.success()
    }

    /**
     * 根据ID切换启用状态
     */
    @PutMapping("/{id}")
    suspend fun toggleEnable(@PathVariable id: Long?) = roleService.toggleEnabled(id).toJson()
}