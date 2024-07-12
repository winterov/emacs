package ru.emacs.controllers


import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import jakarta.validation.Validator
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.emacs.dtos.AppResponseErrorDto
import ru.emacs.dtos.auth.AuthRequestDto
import ru.emacs.dtos.auth.AuthResponseDto
import ru.emacs.services.AuthenticationService
import java.util.*


@RestController
@RequestMapping("/api/v1/auth")
internal class AuthenticationControllerApiV1 @Autowired constructor(
    private val authenticationService: AuthenticationService,
    private val validator: Validator,
    private val messageSource: MessageSource
) {
    private val log = LoggerFactory.getLogger(AuthenticationControllerApiV1::class.java)

    /*Почта email1@one.ru Пароль 2012 */
    @PostMapping
    fun authentication(@RequestBody authRequest: AuthRequestDto, response: HttpServletResponse): ResponseEntity<Any> {
        val notValidParamsMessage:String =  messageSource.getMessage("auth.notValidParams",null, LocaleContextHolder.getLocale())
        val violations = validator.validate(authRequest)
        if (violations.isNotEmpty()) {
            val stringBuilder=StringBuilder()
            violations.forEach { stringBuilder.append(it.message).append(" ") }
            log.error("не валидные параметры запроса: {}", stringBuilder)
            val errorDto = AppResponseErrorDto(HttpStatus.UNAUTHORIZED, notValidParamsMessage)
            return ResponseEntity(errorDto, HttpStatus.UNAUTHORIZED)
        }
        val accessToken = authenticationService.authentication(
            authRequest.emailOrPhone!!,
            authRequest.password!!, response
        )
        if (accessToken==null){
            val errorDto = AppResponseErrorDto(HttpStatus.UNAUTHORIZED, notValidParamsMessage)
            return ResponseEntity(errorDto, HttpStatus.UNAUTHORIZED)
        }
        return ResponseEntity.ok(AuthResponseDto(accessToken))
    }

    @GetMapping
    fun refresh(request: HttpServletRequest): ResponseEntity<Any> {
        val notValidParamsMessage:String =  messageSource.getMessage("auth.notValidParams",null, LocaleContextHolder.getLocale())
        val cookies = request.cookies
        if (cookies == null) {
            val errorDto = AppResponseErrorDto(HttpStatus.UNAUTHORIZED, notValidParamsMessage)
            return ResponseEntity(errorDto, HttpStatus.UNAUTHORIZED)
        }
        val refreshToken = Arrays.stream(cookies).filter { x: Cookie -> x.name == "token" }
            .findFirst()
        if (refreshToken.isEmpty) {
            val errorDto = AppResponseErrorDto(HttpStatus.UNAUTHORIZED, notValidParamsMessage)
            return ResponseEntity(errorDto, HttpStatus.UNAUTHORIZED)
        }
        val accessToken = authenticationService.authenticationByRefreshToken(refreshToken.get())
        if (accessToken == null) {
            val errorDto = AppResponseErrorDto(HttpStatus.UNAUTHORIZED, notValidParamsMessage)
            return ResponseEntity(errorDto, HttpStatus.UNAUTHORIZED)
        }
        return ResponseEntity.ok(AuthResponseDto(accessToken))
    }
}
