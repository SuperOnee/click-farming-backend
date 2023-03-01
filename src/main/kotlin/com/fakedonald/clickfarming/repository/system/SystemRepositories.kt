package com.fakedonald.clickfarming.repository.system

import com.fakedonald.clickfarming.domain.FundChangeRecord
import com.fakedonald.clickfarming.domain.system.*
import com.fakedonald.clickfarming.domain.system.SystemConfigTaskAdditionalPrice
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository

interface SystemUserRepository : CrudRepository<SystemUser, Long>, JpaSpecificationExecutor<SystemUser>

interface SystemRoleRepository : CrudRepository<SystemRole, Long>, JpaSpecificationExecutor<SystemRole>

interface SystemMenuRepository : CrudRepository<SystemMenu, Long>, JpaSpecificationExecutor<SystemMenu>

interface FundChangeRecordRepository : CrudRepository<FundChangeRecord, Long>,
    JpaSpecificationExecutor<FundChangeRecord>

interface SystemBankItemRepository : CrudRepository<SystemBankItem, Long>, JpaSpecificationExecutor<SystemBankItem>

interface SystemBankCardRepository : CrudRepository<SystemBankCard, Long>, JpaSpecificationExecutor<SystemBankCard>

interface SystemConfigTaskAdditionalPriceRepository : CrudRepository<SystemConfigTaskAdditionalPrice, Long>,
    JpaSpecificationExecutor<SystemConfigTaskAdditionalPrice>