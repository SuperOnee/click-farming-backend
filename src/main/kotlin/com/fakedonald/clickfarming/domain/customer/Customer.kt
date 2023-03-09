package com.fakedonald.clickfarming.domain.customer

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fakedonald.clickfarming.enums.customer.IdentityStatusEnum
import com.fasterxml.jackson.annotation.JsonIgnore
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.annotations.Type
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.math.BigDecimal

@Entity
data class Customer(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,

        // 用户名
        @JvmField
        var username: String = "",

        // 密码
        @JsonIgnore
        @JvmField
        var password: String = "",

        // 提现密码
        @JsonIgnore
        var withdrawPassword: String = "",

        // 余额
        var balance: BigDecimal = 0.toBigDecimal(),

        // 上级用户
        var belongsToUser: String = "",

        // 身份信息
        @Column(columnDefinition = "json")
        @Type(value = JsonType::class)
        var identity: Identity? = null,

        // 收款账号
        @Column(columnDefinition = "json")
        @Type(value = JsonType::class)
        var collectAccount: CollectAccount? = null,

        // 身份信息状态
        @Enumerated
        var identityStatus: IdentityStatusEnum = IdentityStatusEnum.PENDING_SUBMIT,

        // 是否允许接单
        var allowTask: Boolean = false,
) : BaseEntity(), UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}

// 收款账号
data class CollectAccount(
        // 银行
        var bank: String = "",

        // 开户行地区
        var bankLocation: String = "",

        // 开户行
        var issueBank: String = "",

        // 银行卡号
        var bankNo: String = "",

        // 支付宝收款码
        var alipayImg: String = "",

        // 微信收款码
        var wechatImg: String = "",
)

// 身份信息
data class Identity(
        // 真实姓名
        var realName: String,

        // 身份证
        var identity: String,

        var gender: String,

        // 手持
        var identityHanded: String,

        // 正面
        var identityFront: String,

        // 反面
        var identityBack: String,

        // 银行账户
        var bank: String,

        // 省
        var province: String,

        // 市
        var city: String,

        // 支付宝账号
        var alipay: String,
) : Serializable
