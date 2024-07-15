package ru.emacs.users.controllers



import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.emacs.users.dtos.auth.AuthRequestDto
import ru.emacs.users.services.AuthenticationService



@RestController
@RequestMapping("/api/v1/auth")
internal class AuthenticationControllerApiV1 @Autowired constructor(
    private val authenticationService: AuthenticationService,
) {
    private val LOG = LoggerFactory.getLogger(AuthenticationControllerApiV1::class.java)

    /*Почта email1@one.ru Пароль 2012 */
    @PostMapping
    fun authentication(@RequestBody authRequest: AuthRequestDto, response: HttpServletResponse): ResponseEntity<Any> {
        val responseParam = authenticationService.authentication(authRequest,response)
        return ResponseEntity(responseParam.first,responseParam.second)
    }

    @GetMapping
    fun refresh(request: HttpServletRequest): ResponseEntity<Any> {
        val responseParam = authenticationService.authenticationByRefreshToken(request)
        return ResponseEntity(responseParam.first,responseParam.second)
    }
}
