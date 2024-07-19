package ru.emacs.users.repositories

import ru.emacs.users.agregators.EUserStatus

internal interface UserPhoneRepository {
    fun countOfUsageEmail(phone: String,statuses:List<EUserStatus>): Long
}