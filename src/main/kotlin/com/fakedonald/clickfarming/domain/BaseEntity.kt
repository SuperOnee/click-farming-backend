package com.fakedonald.clickfarming.domain

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * 每个对象都存在的BaseEntity
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {

    // 创建时间
    @Column(name = "create_at")
    @CreatedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var createAt: LocalDateTime? = LocalDateTime.now()

    // 修改时间
    @Column(name = "update_at")
    @LastModifiedDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    var updateAt: LocalDateTime? = LocalDateTime.now()
}