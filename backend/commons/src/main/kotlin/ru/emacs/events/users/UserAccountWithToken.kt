package ru.emacs.events.users

data class UserAccountWithToken (
    var email: String,
    val name:   String,
    val surname:   String,
    val token: String
)