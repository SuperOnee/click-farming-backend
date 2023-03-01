package com.fakedonald.clickfarming.service.system

import com.fakedonald.clickfarming.domain.system.SystemMenu
import com.fakedonald.clickfarming.repository.system.SystemMenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

interface SystemMenuService {

    suspend fun queryTree(): Collection<SystemMenu>
}

@Service
class SystemMenuServiceImpl(val menuRepository: SystemMenuRepository) : SystemMenuService {
    override suspend fun queryTree(): Collection<SystemMenu> {
        val list = withContext(Dispatchers.IO) {
            menuRepository.findAll()
        }
        val parentMap = list.filter { it.parentId == 0L }.associateBy { it.id }
        val childrenMap = list.filter { it.parentId != 0L }.groupBy({ it.parentId }, { it })
        parentMap.forEach {
            val mappedChildren = childrenMap[it.key] ?: listOf<SystemMenu>() as Collection<SystemMenu>
            it.value.children = mutableSetOf()
            (it.value.children as MutableSet).addAll(mappedChildren)
        }
        return parentMap.values
    }

}
