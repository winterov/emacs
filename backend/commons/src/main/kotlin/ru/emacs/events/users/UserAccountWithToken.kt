package ru.emacs.events.users

data class UserAccountWithToken (
    val email: String,
    val phone:String,
    val name:   String,
    val surname:   String,
    val token: String
)