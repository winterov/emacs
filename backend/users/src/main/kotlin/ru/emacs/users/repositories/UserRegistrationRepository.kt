package ru.emacs.users.repositories



import ru.emacs.users.agregators.EUserStatus
import java.time.LocalDateTime


internal interface UserRegistrationRepository {
    fun saveNewUser(
        email: String,
        phone: String?,
        passwordHash: String,
        credentialExpired: LocalDateTime,
        status: EUserStatus,
        createdAt: LocalDateTime
    ): Long?
}
