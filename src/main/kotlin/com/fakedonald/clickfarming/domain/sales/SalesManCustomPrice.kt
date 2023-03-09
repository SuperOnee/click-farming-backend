package com.fakedonald.clickfarming.domain.sales

import com.fakedonald.clickfarming.domain.BaseEntity
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import java.math.BigDecimal


/**
 * 业务员定价
 */
@Entity
data class SalesManCustomPrice(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    // 专属定价 所属 业务员
    val username: String,

    // 分站ID
    var subSiteId: Long?,

    /**
     * 配置列表
     */
    @Column(columnDefinition = "json")
    @Type(value = JsonType::class)
    var configList: List<SalesManCustomPriceConfigItem> = listOf()
) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SalesManCustomPrice

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}

/**
 * 定价item
 */
data class SalesManCustomPriceConfigItem(
    // 金额
    var amount: BigDecimal,

    // 是否为百分比
    var percent: Boolean,
)
