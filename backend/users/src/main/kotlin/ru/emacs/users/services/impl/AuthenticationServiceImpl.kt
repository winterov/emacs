package ru.emacs.users.services.impl



import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Validator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.emacs.dtos.AppResponseErrorDto
import ru.emacs.properties.models.SecurityProperties
import ru.emacs.properties.services.SecurityPropertiesService
import ru.emacs.users.agregators.UserAccount
import ru.emacs.users.dtos.auth.AuthRequestDto
import ru.emacs.users.dtos.auth.AuthResponseDto
import ru.emacs.users.services.AuthenticationService
import ru.emacs.users.services.RefreshTokenService
import ru.emacs.users.utils.JwtUtils
import ru.emacs.users.utils.PBFDK2Encoder
import ru.emacs.validators.validateDto
import java.util.*


@Service
internal class AuthenticationServiceImpl @Autowired constructor(
    private val detailsService: UserAccountDetailsService,
    private val refreshTokenService: RefreshTokenService,
    private val encoder: PBFDK2Encoder,
    private val jwtUtil: JwtUtils,
    private val validator: Validator,
    private val messageSource: MessageSource,
    securityPropertiesService: SecurityPropertiesService
) : AuthenticationService {
    companion object {
        private val LOG = LoggerFactory.getLogger(AuthenticationServiceImpl::class.java)
        private const val AUTH_ERROR_ID = "auth.notValidParams"
    }
    private var securityProperties: SecurityProperties = securityPropertiesService.getSecurityProperty()

    @Transactional
    override fun authentication(authRequest: AuthRequestDto, response: HttpServletResponse): Pair<Any, HttpStatus> {
        val errors = validateDto(validator,authRequest)
        if (errors.isNotEmpty()) {
            val errorDto = AppResponseErrorDto(HttpStatus.UNAUTHORIZED, errors)
            return Pair(errorDto,HttpStatus.UNAUTHORIZED)
        }
        val user = detailsService.loadUserByUsername(authRequest.emailOrPhone!!) as UserAccount
        if(!encoder.matches(authRequest.password,user.password)){
            val notValidParamsMessage:String =  messageSource.getMessage(
                AUTH_ERROR_ID,null,
                LocaleContextHolder.getLocale())
            throw UsernameNotFoundException(notValidParamsMessage)
        }
        val accessToken= generateToken(user)
        val refreshToken = refreshTokenService.createRefreshToken(user, Date())
        response.addCookie(createRefreshTokenCookie(refreshToken))
        return Pair(AuthResponseDto(accessToken), HttpStatus.OK)
    }
    @Transactional
    override fun authenticationByRefreshToken(request: HttpServletRequest): Pair<Any, HttpStatus> {
        val cookies= request.cookies
        if(cookies == null || cookies.isEmpty()) {
            val notValidParamsMessage:String =  messageSource.getMessage(
                AUTH_ERROR_ID,null,
                LocaleContextHolder.getLocale())
            return Pair(notValidParamsMessage, HttpStatus.UNAUTHORIZED)
        }
        val refreshToken = Arrays.stream(cookies).filter { x: Cookie -> x.name == "token" }.findFirst()
        if(refreshToken.isEmpty) {
            val notValidParamsMessage:String =  messageSource.getMessage(
                AUTH_ERROR_ID,null,
                LocaleContextHolder.getLocale())
            return Pair(notValidParamsMessage, HttpStatus.UNAUTHORIZED)
        }
        val userAccount = refreshTokenService.checkRefreshToken(refreshToken.get().value)
        if(userAccount == null) {
            val notValidParamsMessage:String =  messageSource.getMessage(
                AUTH_ERROR_ID,null,
                LocaleContextHolder.getLocale())
            return Pair(notValidParamsMessage, HttpStatus.UNAUTHORIZED)
        }
        val token = generateToken(userAccount)
        return Pair(token, HttpStatus.OK)
    }

    private fun generateToken(userAccount: UserAccount):String{
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
    private fun createRefreshTokenCookie(refreshToken: String): Cookie {
        val cookie = Cookie("token", refreshToken)
        cookie.isHttpOnly = true
        cookie.maxAge = securityProperties.jwtProperties.jwtRefreshLifetime!!
        return cookie
    }
}
