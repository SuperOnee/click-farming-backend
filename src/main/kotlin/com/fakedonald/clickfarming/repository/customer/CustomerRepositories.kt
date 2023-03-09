package com.fakedonald.clickfarming.repository.customer

import com.fakedonald.clickfarming.domain.customer.Customer
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.CrudRepository

interface CustomerRepository : CrudRepository<Customer, Long>, JpaSpecificationExecutor<Customer>