package ru.emacs.repositories

import ru.emacs.agregators.UserAccount


internal interface UserDetailsRepository {
    fun loadByEmail(email: String): UserAccount?
    fun loadByPhone(phone: String): UserAccount?
}
