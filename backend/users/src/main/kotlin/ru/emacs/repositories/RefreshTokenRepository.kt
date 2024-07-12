package ru.emacs.repositories


import ru.emacs.agregators.UserAccount
import java.util.*


internal interface RefreshTokenRepository {
    fun saveRefreshToken(id: Long, expired: Date, token: String)

    fun checkRefreshToken(refreshToken: String): UserAccount?
}
