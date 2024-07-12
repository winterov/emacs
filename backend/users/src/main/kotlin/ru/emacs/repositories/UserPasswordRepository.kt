package ru.emacs.repositories

import java.time.LocalDateTime


internal interface UserPasswordRepository {
    fun updatePassword(
        userId: Long,
        passwordHash: String,
        credentialExpired: LocalDateTime,
        updatedAt: LocalDateTime
    ): Int


}
