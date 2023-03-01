package com.fakedonald.clickfarming.domain.system

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fakedonald.clickfarming.enums.system.BankTypeEnum
import jakarta.persistence.*
import org.hibernate.Hibernate
import kotlin.jvm.Transient

/**
 * @author nathan
 * @date 2023/2/15 18:08
 */
@Entity
data class SystemBankCard(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    // 账号类型
    @Enumerated
    var bankType: BankTypeEnum = BankTypeEnum.BANK,

    // 银行名称
    var bankName: String,

    // 卡号
    var account: String = "",

    // 户名
    var realName: String = "",

    // 开户行
    var issueBank: String = "",

    // 收款码
    var receiveCode: String = "",

    // 是否默认
    var isDefault: Boolean = false,

    // 转账密码, 用于校验
    @Transient
    var transferPassword: String? = "",

    ) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SystemBankCard

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createAt = $createAt , updateAt = $updateAt , bankType = $bankType , account = $account , realName = $realName , issueBank = $issueBank , receiveCode = $receiveCode , isDefault = $isDefault )"
    }
}