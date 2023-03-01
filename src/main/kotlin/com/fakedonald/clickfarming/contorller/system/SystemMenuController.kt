package com.fakedonald.clickfarming.contorller.system

import com.fakedonald.clickfarming.extension.toJson
import com.fakedonald.clickfarming.service.system.SystemMenuService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author nathan
 * @date 2023/2/9 08:09
 */

@RestController
@RequestMapping("/systemMenu")
class SystemMenuController(val menuService: SystemMenuService) {

    @GetMapping
    suspend fun list() = menuService.queryTree().toJson()
}