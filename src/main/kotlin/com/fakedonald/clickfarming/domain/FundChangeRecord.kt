package com.fakedonald.clickfarming.domain

import com.fakedonald.clickfarming.enums.sales.TradeTypeEnum
import com.fakedonald.clickfarming.enums.sales.UserTypeEnum
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.validation.constraints.NotNull
import org.hibernate.Hibernate
import java.math.BigDecimal

/**
 * @author nathan
 * @date 2023/2/12 16:30
 *
 * 资金变动实体类
 */
@Entity
data class FundChangeRecord(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    // 交易流水号
    var transactionNo: String = "",

    // 用户类型
    @NotNull
    @Enumerated
    var userType: UserTypeEnum,

    // 用户ID
    var userId: Long,

    // 用户名
    var username: String = "",

    // 交易类型
    @Enumerated
    var tradeType: TradeTypeEnum = TradeTypeEnum.OTHER,

    // 订单编号
    var orderNo: String? = "",

    // 任务编号
    var taskNo: String? = "",

    // 金额
    var amount: BigDecimal = BigDecimal.ZERO,

    // 余额
    var balance: BigDecimal = BigDecimal.ZERO,

    // 备注
    var remark: String? = "",

    // 操作人
    var operatedUser: String = "",

    ) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as FundChangeRecord

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createAt = $createAt , updateAt = $updateAt , transactionNo = $transactionNo , userType = $userType , username = $username , tradeType = $tradeType , orderNo = $orderNo , taskNo = $taskNo , amount = $amount , balance = $balance , remark = $remark )"
    }
}