package ru.emacs.users.agregators


import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime


internal class UserAccount : UserDetails {
    var id: Long? = null
    var email: String? = null
    var isEmailVerified: Boolean = false
    var status: EUserStatus = EUserStatus.NEW_USER
    var phone: String? = null
    var isPhoneVerified: Boolean = false
    var psword: String? = null
    var credentialExpiredTime: LocalDateTime? = null
    var createdAt: LocalDateTime? = null
    var updatedAt: LocalDateTime? = null
    var roles: MutableSet<AppRole> = HashSet()


    override fun getAuthorities(): Collection<AppAuthority> {
        val authorities:MutableSet<AppAuthority> = HashSet()
        roles.forEach{
            it.authorities.forEach{ auth->
                authorities.add(auth)
            }
        }
        return authorities
    }

    override fun getPassword(): String {
        return psword!!
    }

    override fun getUsername(): String {
        return email!!
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return !(this.status == EUserStatus.DISMISSED || (this.status == EUserStatus.BANNED) || (this.status == EUserStatus.DELETED))
    }

    override fun isCredentialsNonExpired(): Boolean {
        return LocalDateTime.now().isBefore(this.credentialExpiredTime)
    }

    override fun isEnabled(): Boolean {
        return this.status == EUserStatus.ACTIVE || (this.status == EUserStatus.NEW_USER)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as UserAccount
        return id == other.id
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}
