package com.fakedonald.clickfarming.domain.common

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fakedonald.clickfarming.enums.StateTypeEnum
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.Hibernate

/**
 * @author nathan
 * @date 2023/2/25 22:15
 *
 * 银行卡实体
 */
@Entity
data class UserBankCard(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    // 用户ID
    var userId: Long,

    // 银行卡类型
    var bankType: String = "",

    // 开户行
    var issueBank: String = "",

    // 卡号
    var account: String = "",

    // 持卡人姓名
    var realName: String = "",

    // 用户类型
    var userType: UserTypeEnum?,

    // 审核状态
    var state: StateTypeEnum = StateTypeEnum.PENDING,


    ) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as UserBankCard

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}