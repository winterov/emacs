package ru.emacs.users.repositories



import ru.emacs.users.agregators.EUserStatus
import ru.emacs.users.agregators.EmailApprovedToken
import java.time.LocalDateTime

internal interface UserEmailRepository {
    fun saveVerifiedEmailToken(userId: Long, token: String, expired: LocalDateTime): Int

    fun deleteUsedEmailApprovedTokenByUserId(id: Long): Int

    fun getVerifiedToken(email: String): EmailApprovedToken?

    fun updateUserEmailStatusByUserId(userId: Long, status: Boolean): Int

    fun countOfUsageEmail(email: String,statuses:List<EUserStatus>): Long
}
