package ru.emacs.users.services.impl

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
    override fun createRefreshToken(userAccount: UserAccount, issuedDate: Date):String {
        val refreshExpire = Date(issuedDate.time + securityProperties.jwtProperties.jwtRefreshLifetime!!)
        val refreshToken = jwtUtil.generateRefreshTokenFromEmail()
        refreshTokenRepository.saveRefreshToken(userAccount.id!!, refreshExpire, refreshToken)
        return refreshToken
    }
    @Transactional
    override fun checkRefreshToken(refreshToken: String?): UserAccount? {
        return refreshTokenRepository.checkRefreshToken(refreshToken!!)
    }


}
