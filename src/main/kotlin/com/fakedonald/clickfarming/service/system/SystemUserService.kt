package com.fakedonald.clickfarming.service.system

import com.fakedonald.clickfarming.domain.system.SystemUser
import com.fakedonald.clickfarming.extension.CustomException
import com.fakedonald.clickfarming.extension.equal
import com.fakedonald.clickfarming.extension.generatePageRequest
import com.fakedonald.clickfarming.extension.notFound
import com.fakedonald.clickfarming.repository.system.SystemUserRepository
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.jpa.domain.Specification
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service

interface SystemUserService {

    suspend fun queryPageData(): Page<SystemUser>

    suspend fun saveEntity(request: SystemUser): SystemUser

    suspend fun deleteById(id: Long?): Unit?
    suspend fun toggleEnabled(id: Long): Boolean
}

@Service
class SystemUserServiceImpl(
    private val userRepository: SystemUserRepository,
    private val passwordEncoder: BCryptPasswordEncoder
) : SystemUserService {
    override suspend fun queryPageData(): Page<SystemUser> {
        val pageRequest = generatePageRequest()

        return userRepository.findAll(null, pageRequest)
    }

    @Transactional
    override suspend fun saveEntity(request: SystemUser): SystemUser {
        // create
        return request.id?.let {
            val entity = userRepository.findById(it).notFound()
            entity.username = request.username
            entity.userRoleSet = request.userRoleSet
            // username exist
            val userExistOptional = userRepository.findOne(SystemUser::username.equal(request.username))
            if (userExistOptional.isPresent && userExistOptional.get().id != it) {
                throw CustomException("${userExistOptional.get().username}已存在")
            } else {
                return userRepository.save(entity)
            }
        } ?: run {
            // update
            request.password = passwordEncoder.encode("123456")
            request.fundOperatePassword = passwordEncoder.encode("123456")
            request.transferPassword = passwordEncoder.encode("123456")
            // username exist
            userRepository.findOne(SystemUser::username.equal(request.username)).ifPresent {
                throw CustomException("${it.username}已存在")
            }.run {
                userRepository.save(request)
            }
        }
    }

    @Transactional
    override suspend fun deleteById(id: Long?) = id?.let { userRepository.deleteById(it) }

    @Transactional
    override suspend fun toggleEnabled(id: Long): Boolean {
        val entity = withContext(Dispatchers.IO) {
            userRepository.findById(id)
        }.notFound()
        return entity.enabled.also {
            entity.enabled = !it
            userRepository.save(entity)
        }
    }
}