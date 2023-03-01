package com.fakedonald.clickfarming.domain.common

import com.fakedonald.clickfarming.enums.StateTypeEnum
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Transient
import org.hibernate.Hibernate
import java.math.BigDecimal

@Entity
data class UserWithdrawRequest(

    @Id
    var id: Long? = null,

    // 用户ID
    var userId: Long,

    // 绑定的银行卡ID
    var bankCardId: Long,

    // 用户类型
    @Enumerated
    var userType: UserTypeEnum,

    // 审核状态
    var state: StateTypeEnum? = StateTypeEnum.PENDING,

    // 提现金额
    var amount: BigDecimal,

    // 提现密码
    @Transient
    var withdrawPassword: String,
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserWithdrawRequest

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}