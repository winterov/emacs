package ru.emacs.users.controllers



import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.emacs.dtos.AppResponseErrorDto
import ru.emacs.users.dtos.email.ApprovedEmailRequestDto
import ru.emacs.users.services.UserEmailService

@RestController
@RequestMapping("api/v1/users/email")
internal class UserEmailControllerApiV1 @Autowired constructor(
    private val userEmailService: UserEmailService
) {
    @PutMapping("approved")
    fun approvedEmail(@RequestBody dto: ApprovedEmailRequestDto): ResponseEntity<Any> {
        val responseParam = userEmailService.approvedUserEmail(dto)
        return ResponseEntity(responseParam.first,responseParam.second)
    }

    @GetMapping("check")
    fun emailBusyCheck(@RequestParam email: String): ResponseEntity<Any> {
        val emailValidator = EmailValidator()
        if (!emailValidator.isValid(email, null)) {
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST, "Невалидные параметры запроса")
            return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
        }
        if (userEmailService.emailBusyCheck(email)) {
            val errorDto = AppResponseErrorDto(HttpStatus.CONFLICT, "email $email занят другим аккаунтом")
            return ResponseEntity(errorDto, HttpStatus.CONFLICT)
        }
        return ResponseEntity<Any>(HttpStatus.OK)
    }
}
