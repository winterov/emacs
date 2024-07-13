package ru.emacs.users.repositories



import ru.emacs.users.agregators.PasswordChangeToken
import java.time.LocalDateTime


internal interface ChangePasswordTokenRepository {
    fun saveChangePasswordToken(
        userId: Long,
        expireDate: LocalDateTime,
        token: String
    ): Int

    fun getTokenByUserEmail(email: String): PasswordChangeToken?

    fun deleteUsedToken(id: Long)
}
