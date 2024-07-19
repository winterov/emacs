package ru.emacs.users.controllers



import jakarta.validation.Validator
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.emacs.dtos.AppResponseErrorDto
import ru.emacs.users.dtos.profile.request.UserRegistrationRequestDto
import ru.emacs.users.services.UserRegistrationService

@RestController
@RequestMapping("api/v1/users/registration")
internal class UserRegistrationControllerApiV1
    (
    private val userRegistrationService: UserRegistrationService
    ) {
        @PostMapping
        fun registration(@RequestBody dto: UserRegistrationRequestDto): ResponseEntity<Any> {
            val responseParam = userRegistrationService.createNewUserAccount(dto)
            return ResponseEntity(responseParam.first,responseParam.second)
        }
}