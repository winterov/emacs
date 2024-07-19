package ru.emacs.users.services

import org.springframework.http.HttpStatus
import ru.emacs.users.dtos.email.ApprovedEmailRequestDto


internal interface UserEmailService {
    fun approvedUserEmail(dto: ApprovedEmailRequestDto): Pair<Any?, HttpStatus>
    fun generateVerifiedEmailTokenAndSend(email: String?): Pair<Any?, HttpStatus>
    fun emailBusyCheck(email: String): Pair<Any?, HttpStatus>
}
