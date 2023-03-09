package com.fakedonald.clickfarming.repository.merchant

import com.fakedonald.clickfarming.domain.merchant.Merchant
import com.fakedonald.clickfarming.domain.merchant.MerchantShop
import com.fakedonald.clickfarming.domain.merchant.MerchantTopUpRecord
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository

/**
 * @author nathan
 * @date 2023/2/25 20:03
 */
interface MerchantRepository : CrudRepository<Merchant, Long>, JpaSpecificationExecutor<Merchant>

interface MerchantShopRepository : CrudRepository<MerchantShop, Long>, JpaSpecificationExecutor<MerchantShop>

interface MerchantTopUpRecordRepository : CrudRepository<MerchantTopUpRecord, Long>, JpaSpecificationExecutor<MerchantTopUpRecord>