package ru.emacs.users.services.impl

import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.emacs.properties.models.SecurityProperties
import ru.emacs.properties.services.SecurityPropertiesService
import ru.emacs.users.agregators.UserAccount
import ru.emacs.users.repositories.RefreshTokenRepository
import ru.emacs.users.services.RefreshTokenService
import ru.emacs.users.utils.JwtUtils
import java.util.*


@Service
internal class RefreshTokenServiceImpl @Autowired constructor(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtUtil: JwtUtils,
    securityPropertiesService: SecurityPropertiesService
    ) : RefreshTokenService {
    private var securityProperties: SecurityProperties = securityPropertiesService.getSecurityProperty()
    @Transactional
    override fun generateRefreshToken(userAccount: UserAccount, issuedDate: Date, response: HttpServletResponse) {
        val refreshExpire = Date(issuedDate.time + securityProperties.jwtProperties.jwtRefreshLifetime!!)
        val refreshToken = jwtUtil.generateRefreshTokenFromEmail()
        refreshTokenRepository.saveRefreshToken(userAccount.id!!, refreshExpire, refreshToken)
        response.addCookie(createRefreshTokenCookie(refreshToken))
    }
    @Transactional
    override fun checkRefreshToken(refreshToken: String?): UserAccount? {
        return refreshTokenRepository.checkRefreshToken(refreshToken!!)
    }

    private fun createRefreshTokenCookie(refreshToken: String): Cookie {
        val cookie = Cookie("token", refreshToken)
        cookie.isHttpOnly = true
        cookie.maxAge = securityProperties.jwtProperties.jwtRefreshLifetime!!
        return cookie
    }
}
