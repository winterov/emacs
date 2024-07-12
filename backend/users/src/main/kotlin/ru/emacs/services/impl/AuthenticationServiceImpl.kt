package ru.emacs.services.impl



import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.emacs.agregators.UserAccount
import ru.emacs.models.SecurityProperties
import ru.emacs.services.AuthenticationService
import ru.emacs.services.RefreshTokenService
import ru.emacs.services.SecurityPropertiesService
import ru.emacs.utils.JwtUtils
import ru.emacs.utils.PBFDK2Encoder
import java.util.*


@Service
internal class AuthenticationServiceImpl @Autowired constructor(
    private val detailsService: UserAccountDetailsService,
    private val refreshTokenService: RefreshTokenService,
    private val encoder: PBFDK2Encoder,
    private val jwtUtil: JwtUtils,
    securityPropertiesService: SecurityPropertiesService
) : AuthenticationService {
    private var securityProperties: SecurityProperties = securityPropertiesService.getSecurityProperty()

    @Transactional
    override fun authentication(emailOrPhone: String, password: String, response: HttpServletResponse): String {
        val user = detailsService.loadUserByUsername(emailOrPhone) as UserAccount
        if(!encoder.matches(password,user.password)){
            throw UsernameNotFoundException("Неверные учетные данные пользователя")
        }
        val issuedDate = Date()
        val expireDate = Date(issuedDate.time + securityProperties.jwtProperties.jwtLifetime)
        val accessToken = jwtUtil.generateToken(user.getAuthorities(), user.email, issuedDate, expireDate, user.id)
        refreshTokenService.generateRefreshToken(user, issuedDate, response)
        return accessToken
    }
    @Transactional
    override fun authenticationByRefreshToken(cookie: Cookie): String? {
        val userAccount = refreshTokenService.checkRefreshToken(cookie.value) ?: return null
        val issuedDate = Date()
        val expireDate = Date(issuedDate.time + securityProperties.jwtProperties.jwtLifetime)
        return jwtUtil.generateToken(
            userAccount.getAuthorities(),
            userAccount.email,
            issuedDate,
            expireDate,
            userAccount.id
        )
    }
}
