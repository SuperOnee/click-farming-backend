package com.fakedonald.clickfarming.domain.sales

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import java.math.BigDecimal

/**
 * @author nathan
 * @date 2023/2/15 22:24
 *
 * 定价配置
 */
@Entity
data class SalesManSubSiteConfig(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    // 任务类型
    @Enumerated
    var taskType: TaskTypeEnum,

    // 业务员ID
    var subSiteId: Long,

    // 定价配置列表
    @Column(columnDefinition = "json")
    @Type(value = JsonType::class)
    var configList: List<BigDecimal>,


    ) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SalesManSubSiteConfig

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createAt = $createAt , updateAt = $updateAt , taskType = $taskType , subSiteId = $subSiteId , configList = $configList )"
    }
}