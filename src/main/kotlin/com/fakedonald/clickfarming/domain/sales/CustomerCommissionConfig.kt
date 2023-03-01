package com.fakedonald.clickfarming.domain.sales

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fakedonald.clickfarming.enums.system.TaskTypeEnum
import io.hypersistence.utils.hibernate.type.json.JsonType
import jakarta.persistence.*
import org.hibernate.Hibernate
import org.hibernate.annotations.Type
import java.math.BigDecimal

/**
 * @author nathan
 * @date 2023/2/15 21:02
 */
@Entity
data class CustomerCommissionConfig(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long?,

    // 任务类型
    @Enumerated
    var taskType: TaskTypeEnum,

    // 配置列表
    @Column(columnDefinition = "json")
    @Type(value = JsonType::class)
    var configList: List<ConfigItem>,


    ) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as CustomerCommissionConfig

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createAt = $createAt , updateAt = $updateAt , taskType = $taskType , configList = $configList )"
    }
}

data class ConfigItem(

    // 价格区间
    // 起
    var startPrice: BigDecimal,

    // 止
    var endPrice: BigDecimal,

    // 立返
    // 服务费
    var instantServiceFee: BigDecimal,

    // 佣金
    var commissionPrice: BigDecimal,

    // 显示服务费
    var showServiceFee: BigDecimal,

    // 货返
    // 货返服务费
    var stuffServiceFee: BigDecimal,

    // 货返佣金
    var stuffCommissionPrice: BigDecimal,

    // 级别抽佣
    var levelOne: BigDecimal,
    var levelTwo: BigDecimal,
    var levelThree: BigDecimal,
)