package ru.emacs.repositories



import ru.emacs.agregators.EmailApprovedToken
import java.time.LocalDateTime

internal interface UserEmailRepository {
    fun saveVerifiedEmailToken(userId: Long, token: String, expired: LocalDateTime): Int

    fun deleteUsedEmailApprovedTokenByUserId(id: Long): Int

    fun getVerifiedToken(email: String): EmailApprovedToken?

    fun getVerifiedToken(userId: Long):EmailApprovedToken?

    fun updateUserEmailStatusByUserId(userId: Long, status: Boolean): Int

    fun countOfUsageEmail(email: String): Long
}
