package com.fakedonald.clickfarming.domain.sales

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fakedonald.clickfarming.enums.sales.PrincipalServiceFeeTypeEnum
import com.fakedonald.clickfarming.enums.sales.PrincipalTypeEnum
import com.fakedonald.clickfarming.enums.sales.ThemeTypeEnum
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import java.io.Serializable
import java.math.BigDecimal

/**
 * @author nathan
 * @date 2023/2/15 23:28
 */
@Entity
data class SalesManSubSite(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    // 分站名字
    var siteName: String,

    // 本金返款
    @Enumerated
    var principalType: PrincipalTypeEnum = PrincipalTypeEnum.DEFAULT,

    // 银行
    var bankName: String? = "",

    // 账号
    var account: String? = "",

    // 分站配置
    @Column(columnDefinition = "json")
    @Type(value = JsonType::class)
    var subSiteConfig: SubSiteConfig? = null,


    ) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SalesManSubSite

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createAt = $createAt , updateAt = $updateAt , siteName = $siteName , principalType = $principalType , bankName = $bankName , account = $account , subSiteConfig = $subSiteConfig )"
    }
}

/**
 * 分站配置
 */
data class SubSiteConfig(
    // 域名
    var domain: String? = "",
    // 登录背景图
    var loginBackground: String? = "",
    // LOGO
    var logo: String? = "",
    // 商家端主题风格
    @Enumerated
    var merchantTheme: ThemeTypeEnum? = ThemeTypeEnum.DEFAULT,
    // 商家端推广链接
    var merchantInviteLink: String = "",
    // 分站下业务员商家登录口令
    var merchantLoginPass: String? = "",
    // 客服配置
    // 客服QQ
    var serviceQQ: String? = "",
    // 客服微信(多个)
    var serviceWechatList: List<String>? = listOf(),

    // 开启业务员
    var enableSales: Boolean? = true,
    // 开启商家推荐抽佣
    var enableMerchantCommission: Boolean = false,
    // 商家推荐抽佣比例
    var merchantRate: BigDecimal? = BigDecimal.ZERO,
    // 附加费用抽佣比例
    var additionalRate: BigDecimal? = BigDecimal.ZERO,
    // 好评费用抽佣比例
    var goodCommentRate: BigDecimal? = BigDecimal.ZERO,
    // 开启业务员按服务费分成
    var enabledWithServiceSplit: Boolean? = false,
    // 业务员最高分成比例
    var highestSalesRate: BigDecimal? = BigDecimal.ZERO,
    // 业务员最高分成固定金额
    var highestFixedAmount: BigDecimal? = BigDecimal.ZERO,
    // 本金平台返款手续费收取
    var principalServiceFeeType: PrincipalServiceFeeTypeEnum? = PrincipalServiceFeeTypeEnum.DEFAULT,
    // 本金平台返款手续费比例
    var principalReturnRate: BigDecimal? = BigDecimal.ZERO,
    // 本金平台返款手续费
    var principalReturnFee: BigDecimal? = BigDecimal.ZERO,
    // 本金平台返款手续费分站抽佣
    var principalServiceFeeRate: BigDecimal? = BigDecimal.ZERO,

) : Serializable