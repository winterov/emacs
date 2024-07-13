package ru.emacs.users.dtos.password


import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import ru.emacs.validators.Password
import ru.emacs.validators.TokenLength


internal class ChangePasswordRequestDto {
    @field:Email(message = "Не валидный email") @Size(
        min = 5,
        max = 50,
        message = "Не валидный email"
    )
     val email:  String? = null

    @field:Password(message = "Пароль не соответствует требованиям")
    @field:Size(min = 5, max = 50, message = "Пароль не соответствует требованиям")
    val password:  String? = null

    @field:TokenLength(type = TokenLength.ValidatorType.PASSWORD_TOKEN)
    val token: String? = null
}
