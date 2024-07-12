package ru.emacs.services

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse


interface AuthenticationService {
    fun authentication(
        emailOrPhone: String,
        password: String,
        response: HttpServletResponse
    ): String?

    fun authenticationByRefreshToken(cookie: Cookie): String?
}
