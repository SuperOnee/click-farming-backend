package com.fakedonald.clickfarming.repository

import com.fakedonald.clickfarming.domain.common.UserWithdrawRequest
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository

interface UserWithdrawRequestRepository : CrudRepository<UserWithdrawRequest, Long>, JpaSpecificationExecutor<UserWithdrawRequest>