package com.fakedonald.clickfarming.domain.sales

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fakedonald.clickfarming.enums.sales.SalesManTypeEnum
import com.fakedonald.clickfarming.enums.sales.SharePermissionEnum
import com.fasterxml.jackson.annotation.JsonIgnore
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.math.BigDecimal

/**
 * @author nathan
 * @date 2023/2/9 23:30
 *
 * 业务员
 */
@Entity
@Table(indexes = [Index(columnList = "shareCode")])
data class SalesMan(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    var id: Long? = null,

    // 用户名
    @JvmField
    var username: String = "",

    // 密码
    @JsonIgnore
    @JvmField
    var password: String,

    // 余额
    var balance: BigDecimal = BigDecimal.ZERO,

    // 冻结余额
    var frozeBalance: BigDecimal? = BigDecimal.ZERO,

    // 分享码
    var shareCode: String? = "",

    // 是否启用
    var enabled: Boolean = false,

    // 设置相关........................................................................................................

    // 分享权限: 0: 关闭, 1: 不限, 2: 试客, 3: 商家
    @Enumerated
    var sharePermission: SharePermissionEnum = SharePermissionEnum.CLOSED,

    // 商家抽佣比例
    var businessRate: BigDecimal = BigDecimal.ZERO,

    // 试客抽佣比例
    var customerRate: BigDecimal = BigDecimal.ZERO,

    // 商家抽佣配置
    @Column(columnDefinition = "json")
    @Type(value = JsonType::class)
    var businessCommissionConfig: List<BigDecimal> = listOf(),

    // 商家抽佣配置
    @Column(columnDefinition = "json")
    @Type(value = JsonType::class)
    var customerCommissionConfig: List<BigDecimal> = listOf(),

    // 是否为分站
    var subSite: Boolean? = false,

    // 关联分站ID
    var subSiteId: Long? = null,

    // 上级业务员
    var belongsToUser: String? = null,

    // 业务员类型
    var salesManType: SalesManTypeEnum = SalesManTypeEnum.ALL,

    // 提现密码
    @JsonIgnore
    var withdrawPassword: String,

    // 备注
    var remark: String? = "",

    // 是否使用默认支付密码
    var defaultWithdrawPassword: Boolean,

    ) : BaseEntity(), UserDetails {

    @JsonIgnore
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = mutableListOf()

    @JsonIgnore
    override fun getPassword() = password

    override fun getUsername() = username

    @JsonIgnore
    override fun isAccountNonExpired() = true

    @JsonIgnore
    override fun isAccountNonLocked() = true

    @JsonIgnore
    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = enabled
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SalesMan

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createAt = $createAt , updateAt = $updateAt , username = $username , password = $password , balance = $balance , frozeBalance = $frozeBalance , shareCode = $shareCode , enabled = $enabled , sharePermission = $sharePermission , businessRate = $businessRate , customerRate = $customerRate , businessCommissionConfig = $businessCommissionConfig , customerCommissionConfig = $customerCommissionConfig , subSite = $subSite , subSiteId = $subSiteId , belongsToUser = $belongsToUser )"
    }
}