package ru.emacs.users.dtos.password



import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import ru.emacs.validators.Password
import ru.emacs.validators.TokenLength


internal data class ChangePasswordRequestDto (
    @NotBlank
     val email:  String?,
    @field:Password(message = "Пароль не соответствует требованиям")
    @field:Size(min = 5, max = 50, message = "Пароль не соответствует требованиям")
    val password:  String?,
    @field:TokenLength(type = TokenLength.ValidatorType.PASSWORD_TOKEN)
    val token: String?
)
