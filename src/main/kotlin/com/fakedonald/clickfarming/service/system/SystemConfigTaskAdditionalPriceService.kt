package com.fakedonald.clickfarming.service.system

import com.fakedonald.clickfarming.domain.system.ConfigItem
import com.fakedonald.clickfarming.domain.system.SystemConfigTaskAdditionalPrice
import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import com.fakedonald.clickfarming.extension.CustomException
import com.fakedonald.clickfarming.extension.equal
import com.fakedonald.clickfarming.repository.system.SystemConfigTaskAdditionalPriceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrDefault

interface SystemConfigTaskAdditionalPriceService {

    suspend fun queryData(taskType: TaskTypeEnum): SystemConfigTaskAdditionalPrice

    suspend fun saveEntity(configTaskAdditionalPrice: SystemConfigTaskAdditionalPrice): SystemConfigTaskAdditionalPrice
}

@Service
class SystemConfigTaskAdditionalPriceServiceImpl(
    val configTaskAdditionalPriceRepository:
    SystemConfigTaskAdditionalPriceRepository
) : SystemConfigTaskAdditionalPriceService {
    @OptIn(ExperimentalStdlibApi::class)
    override suspend fun queryData(taskType: TaskTypeEnum): SystemConfigTaskAdditionalPrice {
        val defaultConfigTaskAdditionalPrice = SystemConfigTaskAdditionalPrice(null, taskType, buildList {
            for (i in 1..38) this.add(ConfigItem())
        })
        return configTaskAdditionalPriceRepository.findOne(
            SystemConfigTaskAdditionalPrice::taskType.equal(taskType)
        ).getOrDefault(defaultConfigTaskAdditionalPrice)
    }

    override suspend fun saveEntity(configTaskAdditionalPrice: SystemConfigTaskAdditionalPrice): SystemConfigTaskAdditionalPrice {
        val oneResult = configTaskAdditionalPriceRepository.findOne(
            SystemConfigTaskAdditionalPrice::taskType.equal(configTaskAdditionalPrice.taskType)
        )

        return if (oneResult.isPresent) {
            if (oneResult.get().id == configTaskAdditionalPrice.id) {
                withContext(Dispatchers.IO) {
                    configTaskAdditionalPriceRepository.save(configTaskAdditionalPrice)
                }
            } else throw CustomException("数据重复添加")
        } else {
            withContext(Dispatchers.IO) {
                configTaskAdditionalPriceRepository.save(configTaskAdditionalPrice)
            }
        }
    }

}