package ru.emacs.users.services


import jakarta.servlet.http.HttpServletResponse
import ru.emacs.users.agregators.UserAccount
import java.util.*


internal interface RefreshTokenService {
    fun generateRefreshToken(userAccount: UserAccount, issuedDate: Date, response: HttpServletResponse)

    fun checkRefreshToken(refreshToken: String?): UserAccount?
}
