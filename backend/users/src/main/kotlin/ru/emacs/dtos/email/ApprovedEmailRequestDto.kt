package ru.emacs.dtos.email


import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size
import ru.emacs.validators.TokenLength


internal class ApprovedEmailRequestDto {
    @field:Email(message = "Не валидный email")
    @field:Size(
        min = 5,
        max = 50,
        message = "Не валидный email"
    )
    var email: String? = null

    @field:TokenLength(
        type = TokenLength.ValidatorType.EMAIL_TOKEN
    )
    var token: String? = null
}
