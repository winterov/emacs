package ru.emacs.users.dtos.auth

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

internal class AuthRequestDto {
     @field: NotEmpty(message = "{validation.phoneOrEmail.NotEmpty}")
     @field:Size(min = 4, max = 50, message ="{auth.notValidParams}" )
     var emailOrPhone:String? = null
     @field:NotEmpty(message = "{validation.password.NotEmpty}")
     @field:Size(min = 4, max = 50,message = "{auth.notValidParams}")
     var password: String? = null
}
