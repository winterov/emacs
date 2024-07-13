package ru.emacs.users.agregators

import java.time.LocalDateTime


internal class PasswordChangeToken {
    var userId: Long? = null
    var token: String? = null
    var createdAt: LocalDateTime? = null
    var expired: LocalDateTime? = null
}
