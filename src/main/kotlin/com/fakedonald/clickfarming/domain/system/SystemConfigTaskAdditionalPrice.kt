package com.fakedonald.clickfarming.domain.system

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import java.io.Serializable
import java.math.BigDecimal

/**
 * @author nathan
 * @date 2023/2/21 14:25
 */
@Entity
data class SystemConfigTaskAdditionalPrice(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    // 任务类型
    var taskType: TaskTypeEnum,

    // 配置列表`
    @Column(columnDefinition = "json")
    @Type(value = JsonType::class)
    var configList: List<ConfigItem>,


    ) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SystemConfigTaskAdditionalPrice

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}

data class ConfigItem(
    // 服务费
    var serviceFee: BigDecimal = BigDecimal.ZERO,
    // 佣金
    var commission: BigDecimal = BigDecimal.ZERO,
    // 是否启用
    var enabeld: Boolean = false,
    // 默认
    var isDefault: Boolean = false,
    // 排序
    var sort: Int = 0,
) : Serializable