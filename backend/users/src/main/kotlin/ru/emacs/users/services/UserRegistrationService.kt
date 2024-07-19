package ru.emacs.users.services

import org.springframework.http.HttpStatus
import ru.emacs.users.dtos.profile.request.UserRegistrationRequestDto


internal interface UserRegistrationService {
    fun createNewUserAccount(
        dto: UserRegistrationRequestDto
    ): Pair<Any?, HttpStatus>
}
