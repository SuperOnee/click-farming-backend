package com.fakedonald.clickfarming.service.system

import com.fakedonald.clickfarming.contorller.system.ConfigItem
import com.fakedonald.clickfarming.contorller.system.WrappedConfig
import com.fakedonald.clickfarming.domain.sales.SalesManSubSiteConfig
import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import com.fakedonald.clickfarming.extension.CustomException
import com.fakedonald.clickfarming.extension.equal
import com.fakedonald.clickfarming.repository.sales.CustomerCommissionConfigRepository
import com.fakedonald.clickfarming.repository.sales.SalesManSubSiteConfigRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service
import java.math.BigDecimal

interface SalesManSubSiteConfigService {

    suspend fun queryData(subSiteId: Long): List<ConfigItem>

    suspend fun saveEntity(configList: List<SalesManSubSiteConfig>)
}

@Service
class SalesManSubSiteConfigServiceImpl(
    val commissionConfigRepository: CustomerCommissionConfigRepository,
    val subSiteConfigRepository: SalesManSubSiteConfigRepository
) :
    SalesManSubSiteConfigService {
    /**
     * 查询定价, 构造数据
     */
    override suspend fun queryData(subSiteId: Long): List<ConfigItem> {
        val res = mutableListOf<ConfigItem>()

        val commissionConfigList = withContext(Dispatchers.IO) {
            commissionConfigRepository
                .findAll()
        }

        val configList = subSiteConfigRepository
            .findAll(SalesManSubSiteConfig::subSiteId.equal(subSiteId)).toList()


        val subSiteConfigDic = configList.associateBy { it.taskType }

        val commissionConfigDic = commissionConfigList.associateBy { it.taskType }

        TaskTypeEnum.values().forEach {
            val config = commissionConfigDic[it]
            config?.let { c ->
                val wrappedConfigList = mutableListOf<WrappedConfig>()
                c.configList.forEachIndexed { index, item ->
                    val wrappedConfig = WrappedConfig(
                        item.startPrice,
                        item.endPrice,
                        item.instantServiceFee,
                        BigDecimal.ZERO
                    )

                    val subSiteConfig = subSiteConfigDic[c.taskType]
                    wrappedConfig.instantServiceFeeInput = subSiteConfig?.configList?.get(index) ?: BigDecimal.ZERO
                    wrappedConfigList.add(wrappedConfig)
                }
                res.add(ConfigItem(it, wrappedConfigList))
            }
        }

        return res
    }

    /**
     * 将定价配置保存
     */
    override suspend fun saveEntity(configList: List<SalesManSubSiteConfig>) {
        val subSiteIdSet = configList.map { it.subSiteId }.toSet()

        if (subSiteIdSet.size != 1) throw CustomException("参数错误")

        val subSiteId = subSiteIdSet.first()


        // 查询已存在的配置, 进行更新
        val existedConfigDic = subSiteConfigRepository.findAll(
            SalesManSubSiteConfig::subSiteId.equal(subSiteId)
        ).associateBy { it.taskType }

        // 将传递进来的数据进行保存
        configList.forEach { config ->
            // 存在的进行更新, 未存在的进行新增
            existedConfigDic[config.taskType]?.let { updateEntity ->
                updateEntity.configList = config.configList
                subSiteConfigRepository.save(updateEntity)

                // 否则新增数据
            } ?: run {
                subSiteConfigRepository.save(config)
            }
        }
    }
}







