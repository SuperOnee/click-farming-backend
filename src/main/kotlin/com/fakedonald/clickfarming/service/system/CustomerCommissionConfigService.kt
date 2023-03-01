package com.fakedonald.clickfarming.service.system

import com.fakedonald.clickfarming.domain.sales.CustomerCommissionConfig
import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import com.fakedonald.clickfarming.extension.CustomException
import com.fakedonald.clickfarming.extension.equal
import com.fakedonald.clickfarming.repository.sales.CustomerCommissionConfigRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

interface CustomerCommissionConfigService {

    suspend fun queryByType(taskType: TaskTypeEnum): CustomerCommissionConfig

    suspend fun saveEntity(commissionConfig: CustomerCommissionConfig): CustomerCommissionConfig
}

@Service
class CustomerCommissionConfigServiceImpl(val commissionConfigRepository: CustomerCommissionConfigRepository) :
    CustomerCommissionConfigService {
    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun queryByType(taskType: TaskTypeEnum): CustomerCommissionConfig {
        return commissionConfigRepository
            .findOne(CustomerCommissionConfig::taskType.equal(taskType)).getOrElse {
                CustomerCommissionConfig(null, taskType, listOf())
            }
    }

    override suspend fun saveEntity(commissionConfig: CustomerCommissionConfig): CustomerCommissionConfig {
        val oneResult =
            commissionConfigRepository.findOne(CustomerCommissionConfig::taskType.equal(commissionConfig.taskType))
        return if (oneResult.isPresent) {
            // 存在进行更新, 判断ID相同
            if (oneResult.get().id == commissionConfig.id) {
                withContext(Dispatchers.IO) {
                    commissionConfigRepository.save(commissionConfig)
                }
            } else {
                throw CustomException("数据重复添加")
            }
        } else {
            withContext(Dispatchers.IO) {
                commissionConfigRepository.save(commissionConfig)
            }
        }
    }
}