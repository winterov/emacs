package ru.emacs.users.services


import ru.emacs.users.agregators.UserAccount
import java.util.*


internal interface RefreshTokenService {
    fun createRefreshToken(userAccount: UserAccount,
                           issuedDate: Date):String

    fun checkRefreshToken(refreshToken: String?): UserAccount?
}
