package ru.emacs.users.repositories

import ru.emacs.users.agregators.UserAccount


internal interface UserDetailsRepository {
    fun loadByEmail(email: String): UserAccount?
    fun loadByPhone(phone: String): UserAccount?
}
