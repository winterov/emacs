package ru.emacs.services

import org.springframework.security.core.userdetails.UserDetailsService
import ru.emacs.agregators.UserAccount



internal interface UserAccountService:UserDetailsService {
    fun loadByEmail(email: String): UserAccount?
    fun loadByPhone(phone: String): UserAccount?
}