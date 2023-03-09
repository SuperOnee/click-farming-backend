package com.fakedonald.clickfarming.repository.sales

import com.fakedonald.clickfarming.domain.sales.*
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

/**
 * @author nathan
 * @date 2023/2/10 00:46
 *
 * 业务持久层
 */
interface SalesManRepository : CrudRepository<SalesMan, Long>, JpaSpecificationExecutor<SalesMan>

interface CustomerCommissionConfigRepository : CrudRepository<CustomerCommissionConfig, Long>,
    JpaSpecificationExecutor<CustomerCommissionConfig>

interface SalesManSubSiteConfigRepository : CrudRepository<SalesManSubSiteConfig, Long>,
    JpaSpecificationExecutor<SalesManSubSiteConfig>

interface SalesManSubSiteRepository : CrudRepository<SalesManSubSite, Long>,
    JpaSpecificationExecutor<SalesManSubSite>

interface SalesManCustomPriceRepository : CrudRepository<SalesManCustomPrice, Long>, JpaSpecificationExecutor<SalesManCustomPrice>

interface SalesManMerchantCustomPriceRepository : CrudRepository<SalesManMerchantCustomPrice, Long>, JpaSpecificationExecutor<SalesManMerchantCustomPrice>