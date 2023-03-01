package com.fakedonald.clickfarming.domain.system

import com.fakedonald.clickfarming.domain.BaseEntity
import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import org.hibernate.Hibernate


@Entity
data class SystemRole(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // 系统名称
    @NotEmpty
    var roleName: String = "",

    // 是否启用
    var enabled: Boolean = false,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "system_role_menu",
        joinColumns = [JoinColumn(name = "role_id")],
        inverseJoinColumns = [JoinColumn(name = "menu_id")]
    )
    var systemMenuSet: Set<SystemMenu>? = setOf()


) : BaseEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SystemRole

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createAt = $createAt , updateAt = $updateAt , roleName = $roleName , enabled = $enabled )"
    }
}