package com.fakedonald.clickfarming.domain.sales

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
 * 商家定价表
 */
@Entity
data class SalesManMerchantCustomPrice(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    /**
     * 用户名
     */
    val username: String,

    /**
     * 分站ID
     */
    var subSiteId: Long? = null,

    /**
     * 配置列表
     */
    @Column(columnDefinition = "json")
    @Type(value = JsonType::class)
    var configList: List<MerchantCustomPriceConfigItem> = listOf()

) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SalesManMerchantCustomPrice

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}

/**
 * 商家定价item
 */
data class MerchantCustomPriceConfigItem(
    var taskType: TaskTypeEnum,

    var customPriceList: List<BigDecimal>
) : Serializable