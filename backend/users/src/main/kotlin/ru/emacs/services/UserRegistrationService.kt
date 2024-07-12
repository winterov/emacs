package ru.emacs.services





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
