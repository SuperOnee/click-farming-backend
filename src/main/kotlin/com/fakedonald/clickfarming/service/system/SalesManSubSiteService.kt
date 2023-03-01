package com.fakedonald.clickfarming.service.system

import com.fakedonald.clickfarming.domain.sales.SalesManSubSite
import com.fakedonald.clickfarming.extension.CustomException
import com.fakedonald.clickfarming.extension.notFound
import com.fakedonald.clickfarming.repository.sales.SalesManSubSiteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Service

interface SalesManSubSiteService {

    suspend fun queryById(id: Long): SalesManSubSite

    suspend fun saveConfigData(subSite: SalesManSubSite): SalesManSubSite
    fun saveBankConfig(subSite: SalesManSubSite): SalesManSubSite
}

@Service
class SalesManSubSiteServiceImpl(val subSiteRepository: SalesManSubSiteRepository) : SalesManSubSiteService {

    /**
     * 根据分站ID查询数据
     */
    override suspend fun queryById(id: Long): SalesManSubSite =

        withContext(Dispatchers.IO) {
            subSiteRepository.findById(id)
        }.notFound()


    /**
     * 保存分站配置
     */
    override suspend fun saveConfigData(subSite: SalesManSubSite): SalesManSubSite {
        return subSite.id?.let {
            val entity = subSiteRepository.findById(it).notFound()
            entity.subSiteConfig = subSite.subSiteConfig
            subSiteRepository.save(entity)
        } ?: throw CustomException("参数错误")
    }

    /**
     * 更新银行信息
     */
    override fun saveBankConfig(subSite: SalesManSubSite): SalesManSubSite {
        return subSite.id?.let {
            val entity = subSiteRepository.findById(it).notFound()
            entity.bankName = subSite.bankName
            entity.account = subSite.account
            entity.principalType = subSite.principalType
            subSiteRepository.save(entity)
        } ?: throw CustomException("参数错误")
    }

}