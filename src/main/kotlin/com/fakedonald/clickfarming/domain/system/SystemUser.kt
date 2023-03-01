package com.fakedonald.clickfarming.domain.system

import com.fakedonald.clickfarming.domain.BaseEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*
import jakarta.validation.constraints.NotEmpty
import org.hibernate.Hibernate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
data class SystemUser(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    // 用户名
    @NotEmpty
    @JvmField
    var username: String = "",

    // 登录密码
    @JsonIgnore
    @JvmField
    var password: String = "",

    // 资金调整密码
    @JsonIgnore
    var fundOperatePassword: String = "",

    // 转账密码
    @JsonIgnore
    var transferPassword: String = "",

    // 是否启用
    var enabled: Boolean = false,

    // 关联的角色
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "system_user_role",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var userRoleSet: Set<SystemRole>?,

    ) : BaseEntity(), UserDetails {

    @JsonIgnore
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {

        //将用户权限返回
        val authorities: MutableList<GrantedAuthority> = mutableListOf()
        val menuSet =
            userRoleSet?.map(SystemRole::systemMenuSet)
                ?.flatMap { it?.toSet() ?: setOf() }?.toSet()
        menuSet?.forEach {
            authorities.add(SimpleGrantedAuthority(it.permission))
        }

        authorities.add(SimpleGrantedAuthority("ROLE_admin"))
        return authorities
    }

    override fun getPassword() = password

    override fun getUsername() = username

    @JsonIgnore
    override fun isAccountNonExpired() = true

    @JsonIgnore
    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = enabled
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || Hibernate.getClass(this) != Hibernate.getClass(other)) return false
        other as SystemUser

        return id != null && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    @Override
    override fun toString(): String {
        return this::class.simpleName + "(id = $id , createAt = $createAt , updateAt = $updateAt , username = $username , password = $password , fundOperatePassword = $fundOperatePassword , transferPassword = $transferPassword , enabled = $enabled )"
    }
}