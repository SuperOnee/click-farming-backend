package com.fakedonald.clickfarming.domain.system

import com.fakedonald.clickfarming.domain.BaseEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.hibernate.Hibernate

@Entity
data class SystemMenu(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // 权限
    var permission: String,

    var parentId: Long = 0,

    // 路由路径
    var path: String? = "",

    var enabled: Boolean = false,

    @Transient
    var children: Set<SystemMenu>? = mutableSetOf()

) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SystemMenu

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createAt = $createAt , updateAt = $updateAt , permission = $permission , parentId = $parentId , enabled = $enabled , children = $children )"
    }
}