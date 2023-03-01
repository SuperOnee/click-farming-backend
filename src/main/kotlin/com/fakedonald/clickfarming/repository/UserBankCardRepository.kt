package com.fakedonald.clickfarming.repository

import com.fakedonald.clickfarming.domain.common.UserBankCard
import org.springframework.data.jpa.domain.Specification
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository

/**
 * @author nathan
 * @date 2023/2/25 22:21
 */
interface UserBankCardRepository : CrudRepository<UserBankCard, Long>, JpaSpecificationExecutor<UserBankCard>