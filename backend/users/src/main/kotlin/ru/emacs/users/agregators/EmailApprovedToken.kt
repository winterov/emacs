package ru.emacs.users.agregators

import java.time.LocalDateTime


internal data class EmailApprovedToken(
    var userId: Long,
    var token: String?,
    var createdAt: LocalDateTime,
    var expired: LocalDateTime,
)
