package ru.emacs.users.services

import org.springframework.security.core.userdetails.UserDetailsService
import ru.emacs.users.agregators.UserAccount



internal interface UserAccountService:UserDetailsService {
    fun loadByEmail(email: String): UserAccount?
    fun loadByPhone(phone: String): UserAccount?
}