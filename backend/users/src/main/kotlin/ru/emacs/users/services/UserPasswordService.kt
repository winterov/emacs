package ru.emacs.users.services

import java.nio.file.attribute.UserPrincipal


internal interface UserPasswordService {
    fun createChangePasswordToken(email: String): String?

    fun restorePassword(
        email: String,
        password: String,
        token: String
    ): String?
    fun changePasswordAuthenticatedUser(principal: UserPrincipal,
                                        password: String):String?
}
