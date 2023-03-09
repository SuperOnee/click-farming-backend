package com.fakedonald.clickfarming.domain.merchant

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fakedonald.clickfarming.enums.StateTypeEnum
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Transient
import org.hibernate.Hibernate
import java.math.BigDecimal

@Entity
data class MerchantTopUpRecord(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    // 关联商家id
    var merchantId: Long,

    // 关联的银行实体
    var bankCardId: Long,

    // 充值金额
    var amount: BigDecimal,

    // 审核状态
    var state: StateTypeEnum? = StateTypeEnum.PENDING,

    // 充值截图
    var picture: String,

    // 备注
    var remark: String? = "",

    // 付款账号
    @Transient
    var account: String? = "",

    ) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as MerchantTopUpRecord

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
