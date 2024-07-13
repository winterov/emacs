package ru.emacs.users.services





internal interface UserRegistrationService {
    fun createNewUserAccount(
        email: String,
        phone: String?,
        password: String,
        name: String,
        surname: String,
        lastname: String?
    )
}
