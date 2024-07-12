package ru.emacs.controllers


import jakarta.validation.Validator
import org.hibernate.validator.internal.constraintvalidators.hv.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.emacs.dtos.AppResponseErrorDto
import ru.emacs.dtos.email.ApprovedEmailRequestDto
import ru.emacs.services.UserEmailService

@RestController
@RequestMapping("api/v1/users/email")
internal class UserEmailControllerApiV1 @Autowired constructor(
    private val validator: Validator,
    private val userEmailService: UserEmailService
) {
    @PutMapping("approved")
    fun approvedEmail(@RequestBody dto: ApprovedEmailRequestDto): ResponseEntity<Any> {
        val violations = validator.validate(dto)
        if (violations.isNotEmpty()) {
            val errorMessage: MutableList<String> = ArrayList(4)
            for (violation in violations) {
                errorMessage.add(violation.message)
            }
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST, errorMessage)
            return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
        }
        val errorMessage = userEmailService.approvedUserEmail(dto.email!!, dto.token!!)
        if (errorMessage.isEmpty()) {
            return ResponseEntity.ok(null)
        }
        val errorDto = AppResponseErrorDto(HttpStatus.NOT_ACCEPTABLE, errorMessage)
        return ResponseEntity(errorDto, HttpStatus.NOT_ACCEPTABLE)
    }

    @GetMapping("check/{email}")
    fun emailBusyCheck(@PathVariable email: String): ResponseEntity<Any> {
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
