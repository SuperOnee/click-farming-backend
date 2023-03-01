package com.fakedonald.clickfarming.contorller.system

import com.fakedonald.clickfarming.domain.system.SystemUser
import com.fakedonald.clickfarming.extension.Response
import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.security.ResetAdminPasswordRequest
import com.fakedonald.clickfarming.security.TokenService
import com.fakedonald.clickfarming.service.system.SystemUserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/systemUser")
class SystemUserController(
    private val userService: SystemUserService,
    private val tokenService: TokenService
) {

    /**
     * 查询用户列表
     */
    @GetMapping
    suspend fun queryList() = userService.queryPageData().toJson()

    /**
     * 新增/修改用户
     */
    @PostMapping
    suspend fun save(@Valid @RequestBody request: SystemUser) = userService.saveEntity(request).toJson()

    @PutMapping("/{id}")
    suspend fun toggleEnabled(@PathVariable id: Long) = userService.toggleEnabled(id).toJson()

    /**
     * 删除用户
     */
    @DeleteMapping
    suspend fun delete(id: Long?): Response {
        userService.deleteById(id)
        return Response.success()
    }

    /**
     * 重置密码
     */
    @PutMapping("/resetPassword")
    suspend fun resetPassword(@RequestBody request: ResetAdminPasswordRequest): Response {
        tokenService.resetAdminPassword(request)
        return Response.success()
    }

}