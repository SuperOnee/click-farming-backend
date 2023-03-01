package com.fakedonald.clickfarming.service.system

import com.fakedonald.clickfarming.domain.system.SystemBankItem
import com.fakedonald.clickfarming.extension.CustomException
import com.fakedonald.clickfarming.repository.system.SystemBankItemRepository
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

interface SystemBankItemService {

    suspend fun queryList(): List<SystemBankItem>

    suspend fun saveEntity(bankItem: SystemBankItem): SystemBankItem
}

@Service
class SystemBankItemServiceImpl(
    val bankItemRepository: SystemBankItemRepository
) : SystemBankItemService {
    override suspend fun queryList() = withContext(Dispatchers.IO) {
        bankItemRepository.findAll()
    }.toList()

    @Transactional
    override suspend fun saveEntity(bankItem: SystemBankItem): SystemBankItem {
        bankItem.id?.let { throw CustomException("Persist with id $it") }

        return withContext(Dispatchers.IO) {
            bankItemRepository.save(bankItem)
        }
    }
}