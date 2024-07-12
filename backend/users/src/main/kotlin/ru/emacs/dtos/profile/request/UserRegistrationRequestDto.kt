package ru.emacs.dtos.profile.request


import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import ru.emacs.validators.Password
import ru.emacs.validators.PhoneNumber


internal class UserRegistrationRequestDto {
    @field:Email(regexp = ".*@.*\\..*", message = "{validation.email.NotValid}")
    @field:Size(
        min = 5,
        max = 50,
        message = "{validation.email.NotValid}"
    )
     val email:  String? = null

    @field:PhoneNumber(message = "{validation.phone.NotValid}", required = false)
    @field:Size(min = 5, max = 20, message = "{validation.phone.NotValid}")
     val phone:  String? = null

    @field:Password(message = "{validation.password.Strength}")
    @field:Size(min = 5, max = 50, message = "{validation.password.Strength}")
     val password:  String? = null

    @field:NotBlank(message = "{validation.name.NotEmpty}")
    @field:Size(
        min = 1,
        max = 50,
        message = "{validation.name.Size}"
    )
    val name:   String? = null

    @field:NotBlank(message = "{validation.surname.NotEmpty}")
    @field:Size(
        min = 1,
        max = 50,
        message = "{validation.name.Size}"
    )
     val surname:   String? = null

    @field:Size(max = 50, message = "{validation.lastname.Size}")
     val lastname:  String? = null
}
