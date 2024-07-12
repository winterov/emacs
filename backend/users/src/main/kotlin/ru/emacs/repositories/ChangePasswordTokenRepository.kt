package ru.emacs.repositories



import ru.emacs.agregators.PasswordChangeToken
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
