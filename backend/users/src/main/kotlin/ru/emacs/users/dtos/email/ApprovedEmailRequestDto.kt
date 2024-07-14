package ru.emacs.users.dtos.email



import jakarta.validation.constraints.NotBlank
import ru.emacs.validators.TokenLength


internal data class ApprovedEmailRequestDto(
    @NotBlank
    val email: String?,

    @field:TokenLength(
        type = TokenLength.ValidatorType.EMAIL_TOKEN
    )
    var token: String?
)
