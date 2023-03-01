package com.fakedonald.clickfarming.service.system

import com.fakedonald.clickfarming.domain.system.SystemRole
import com.fakedonald.clickfarming.extension.CustomException
import com.fakedonald.clickfarming.extension.equal
import com.fakedonald.clickfarming.extension.notFound
import com.fakedonald.clickfarming.repository.system.SystemRoleRepository
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

/**
 * @author nathan
 * @date 2023/2/9 07:49
 */
interface SystemRoleService {

    suspend fun queryList(enabled: Boolean?): List<SystemRole>

    suspend fun queryById(id: Long): SystemRole

    suspend fun saveEntity(request: SystemRole): SystemRole

    suspend fun deleteById(id: Long?): Unit?
    fun toggleEnabled(id: Long?): Boolean
}

@Service
class SystemRoleServiceImpl(
    val roleRepository: SystemRoleRepository,
) : SystemRoleService {

    /**
     * 查询列表
     */
    override suspend fun queryList(enabled: Boolean?): List<SystemRole> {
        return withContext(Dispatchers.IO) {
            val roleList = roleRepository.findAll(
                enabled?.let {
                    SystemRole::enabled.equal(enabled)
                }
            ).toList()
            roleList
        }
    }

    override suspend fun queryById(id: Long): SystemRole =
        withContext(Dispatchers.IO) {
            roleRepository.findById(id)
        }.notFound()

    /**
     * 保存数据
     */
    @Transactional
    override suspend fun saveEntity(request: SystemRole): SystemRole {
        // update
        return request.id?.let {
            val entity = roleRepository.findById(it).notFound()
            val roleNameExistOptional = roleRepository.findOne(SystemRole::roleName.equal(request.roleName))
            if (roleNameExistOptional.isPresent && roleNameExistOptional.get().id != it) {
                throw CustomException("${roleNameExistOptional.get().roleName}已存在")
            } else {
                entity.roleName = request.roleName
                entity.systemMenuSet = request.systemMenuSet
                roleRepository.save(entity)
            }

        } ?: run {
            // create
            val roleNameExistOptional = roleRepository.findOne(SystemRole::roleName.equal(request.roleName))
            roleNameExistOptional.ifPresent {
                throw CustomException("${it.roleName}已存在")
            }.run {
                roleRepository.save(request)
            }
        }
    }

    /**
     * 根据ID删除
     */
    @Transactional
    override suspend fun deleteById(id: Long?): Unit? {
        id?.let {
            roleRepository.deleteById(id)
        }
        return null
    }

    @Transactional
    override fun toggleEnabled(id: Long?): Boolean {
        id?.let {
            val entity = roleRepository.findById(id).notFound()
            return entity.enabled.also {
                entity.enabled = !it
                roleRepository.save(entity)
            }
        } ?: throw CustomException("ID 不能为空")
    }

}