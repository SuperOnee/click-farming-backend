package com.fakedonald.clickfarming.domain.merchant

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fakedonald.clickfarming.enums.merchant.ShopTypeEnum
import com.fakedonald.clickfarming.enums.merchant.MerchantStateTypeEnum
import com.fakedonald.clickfarming.enums.sales.PrincipalTypeEnum
import com.fasterxml.jackson.annotation.JsonIgnore
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

/**
 * @author nathan
 * @date 2023/2/25 19:37
 */
@Entity
data class Merchant(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    // 用户名
    @JvmField
    var username: String,

    // 密码
    @JvmField
    var password: String,

    // 余额
    var balance: BigDecimal = BigDecimal.ZERO,

    // 冻结余额
    var frozeBalance: BigDecimal = BigDecimal.ZERO,

    // 联系人
    var contact: String? = "",

    /**
     * 店铺列表
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = [CascadeType.ALL])
    @JoinColumn(name = "merchantId")
    var shopList: List<MerchantShop>? = null,

    // 是否开启淘宝业务
    var taobaoEnabled: Boolean = true,

    // 降权是否包赔
    var insurance: Boolean = false,

    // 佣金折率
    var commissionRate: BigDecimal = BigDecimal.ONE,

    // 本金返款类型
    @Enumerated
    var principalType: PrincipalTypeEnum = PrincipalTypeEnum.DEFAULT,

    // 上级业务员
    var salesManId: Long? = null,

    // 审核状态
    @Enumerated
    var state: MerchantStateTypeEnum = MerchantStateTypeEnum.PENDING,

    // 是否可以发布任务
    var publishTaskEnabled: Boolean = false,

    // 微信号
    var wechat: String = "",

    // QQ号
    var qq: String = "",

    // 备注
    var remark: String = "",

    // 绑定店铺限制
    var shopLimit: Int = 0,


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

    override fun isEnabled() = state == MerchantStateTypeEnum.PASS
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as Merchant

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}
























