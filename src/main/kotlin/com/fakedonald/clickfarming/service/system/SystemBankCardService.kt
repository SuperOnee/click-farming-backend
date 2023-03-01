package com.fakedonald.clickfarming.service.system

import com.fakedonald.clickfarming.contorller.system.BankCardQueryParams
import com.fakedonald.clickfarming.domain.system.SystemBankCard
import com.fakedonald.clickfarming.extension.*
import com.fakedonald.clickfarming.repository.system.SystemBankCardRepository
import com.fakedonald.clickfarming.security.AdminPasswordTypeEnum
import com.fakedonald.clickfarming.security.TokenService
import jakarta.transaction.Transactional
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.data.domain.Page
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

interface SystemBankCardService {

    suspend fun queryPage(params: BankCardQueryParams): Page<SystemBankCard>

    suspend fun saveEntity(bankCard: SystemBankCard): SystemBankCard

    suspend fun deleteEntity(id: Long)
    fun queryList(): List<SystemBankCard>
}

@Service
class SystemBankCardServiceImpl(
    val bankCardRepository: SystemBankCardRepository,
    val tokenService: TokenService
) : SystemBankCardService {
    override suspend fun queryPage(params: BankCardQueryParams): Page<SystemBankCard> {
        val pageRequest = generatePageRequest()
        return bankCardRepository
            .findAll(
                and(
                    SystemBankCard::account.equal(params.account),
                    SystemBankCard::realName.equal(params.realName)
                ),
                pageRequest
            )
    }

    @Transactional
    override suspend fun saveEntity(bankCard: SystemBankCard): SystemBankCard {
        // 校验密码
        val admin = tokenService.getAdminUser()
        val valid =
            tokenService.validateAdminPassword(admin, bankCard.transferPassword ?: "", AdminPasswordTypeEnum.TRANSFER)

        if (!valid) throw CustomException("转账密码错误")

        val count = withContext(Dispatchers.IO) {
            bankCardRepository.count()
        }
        return bankCard.id?.let {
            // update
            if (bankCard.isDefault) {
                bankCardRepository.findAll(Specification.not(SystemBankCard::id.`in`(listOf(it)))).forEach { item ->
                    item.isDefault = false
                    bankCardRepository.save(item)
                }
            } else {
                if (count == 0L) throw CustomException("修改失败, 必须一条为默认")
            }
            bankCardRepository.save(bankCard)
        } ?: run {
            // 新增
            bankCard.isDefault = count == 0L
            bankCardRepository.save(bankCard)
        }
    }

    override suspend fun deleteEntity(id: Long) = withContext(Dispatchers.IO) {
        val entity = bankCardRepository.findById(id).notFound()
        if (entity.isDefault) throw CustomException("不能删除默认银行卡")
        bankCardRepository.deleteById(id)
    }

    override fun queryList(): List<SystemBankCard> = bankCardRepository.findAll(null)

}