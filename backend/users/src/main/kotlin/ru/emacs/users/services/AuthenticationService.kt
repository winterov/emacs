package ru.emacs.users.services

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import ru.emacs.users.dtos.auth.AuthRequestDto



internal interface AuthenticationService {
    fun authentication(
        authRequest: AuthRequestDto,
        response: HttpServletResponse
    ): Pair<Any, HttpStatus>

    fun authenticationByRefreshToken(request: HttpServletRequest): Pair<Any, HttpStatus>
}
