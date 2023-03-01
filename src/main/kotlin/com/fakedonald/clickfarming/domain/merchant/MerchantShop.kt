package com.fakedonald.clickfarming.domain.merchant

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fakedonald.clickfarming.enums.StateTypeEnum
import com.fakedonald.clickfarming.enums.merchant.ShopTypeEnum
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.Hibernate
import java.time.LocalDateTime

/**
 * @author nathan
 * @date 2023/2/26 18:10
 *
 * 商家店铺表
 */
@Entity
data class MerchantShop(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    // 关联商家的ID
    var merchantId: Long?,

    // 店铺类型
    var shopType: ShopTypeEnum,

    // 主营类目
    var mainCategory: String,

    // 店铺掌柜号
    var account: String,

    // 店铺名称
    var shopName: String,

    // 店铺链接
    var shopLink: String,

    // 店铺旗帜
    var shopFlag: String,

    // 插旗备注
    var flagRemark: String,

    // 复购天数
    var repayDays: Int,

    // 发货人
    var deliveryName: String,

    // 发货人电话
    var deliveryPhone: String,

    // 邮编
    var postCode: String,

    // 省, 市,区
    var province: String,
    var city: String,
    var area: String,

    // 详细地址
    var location: String,

    // 审核状态
    @Enumerated
    var state: StateTypeEnum? = StateTypeEnum.PENDING,

    ) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as MerchantShop

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id )"
    }
}