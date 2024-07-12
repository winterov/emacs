package ru.emacs.services




internal interface UserEmailService {
    fun approvedUserEmail(email: String, token: String): List<String>
    fun generateVerifiedEmailToken(email: String): List<String>
    fun emailBusyCheck(email: String): Boolean
}
