package ru.emacs.users.controllers


import jakarta.validation.Validator
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.emacs.dtos.AppResponseErrorDto
import ru.emacs.users.dtos.password.ChangePasswordRequestDto
import ru.emacs.users.services.UserPasswordService


@RestController
@RequestMapping("api/v1/users/password")
internal class UsersPasswordControllerApiV1 @Autowired constructor(
    private val userPasswordService: UserPasswordService,
    private val validator: Validator
) {
    @GetMapping("change")
    fun requestForChangePassword(@RequestParam email: String): ResponseEntity<Any> {
        val emailValidator = EmailValidator()
        if (!emailValidator.isValid(email, null)) {
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST, "Аккаунт $email не существует")
            return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
        }
        val errorMessages = userPasswordService.createChangePasswordToken(email)
        if (errorMessages!=null) {
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST, errorMessages)
            return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(null)
    }

    @PutMapping("change")
    fun changePassword(@RequestBody dto: ChangePasswordRequestDto): ResponseEntity<Any> {
        val violations = validator.validate(dto)
        if (violations.isNotEmpty()) {
            val errorMessage: MutableList<String> = ArrayList(6)
            for (violation in violations) {
                errorMessage.add(violation.message)
            }
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST, errorMessage)
            return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
        }
        val errorMessages = userPasswordService.restorePassword(dto.email!!, dto.password!!, dto.token!!)
        if (errorMessages!=null) {
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST, errorMessages)
            return ResponseEntity(errorDto, HttpStatus.BAD_REQUEST)
        }
        return ResponseEntity.ok(null)
    }
}
