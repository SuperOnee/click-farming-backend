package com.fakedonald.clickfarming.domain.merchant

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fakedonald.clickfarming.enums.StateTypeEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

@Entity
data class MerchantTask(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        // 商家username
        var merchantUsername: String = "",

        // 数据本体
        @Column(columnDefinition = "longtext")
        var json: String = "",

        // 审核状态
        var state: StateTypeEnum = StateTypeEnum.PENDING,

        ) : BaseEntity()
