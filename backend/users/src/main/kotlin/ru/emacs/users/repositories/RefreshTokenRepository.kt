package ru.emacs.users.repositories


import ru.emacs.users.agregators.UserAccount
import java.util.*


internal interface RefreshTokenRepository {
    fun saveRefreshToken(id: Long, expired: Date, token: String)

    fun checkRefreshToken(refreshToken: String): UserAccount?
}
