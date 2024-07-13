package ru.emacs.users.services.impl



import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import ru.emacs.users.agregators.UserAccount
import ru.emacs.users.repositories.UserDetailsRepository
import ru.emacs.users.services.UserAccountService
import ru.emacs.validators.PhoneNumberValidator


@Service
internal class UserAccountDetailsService @Autowired constructor(private val userDetailsRepository: UserDetailsRepository) :
    UserAccountService {
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(authToken: String): UserDetails {
        var userDetails: UserDetails? = null
        val emailValidator = EmailValidator()
        if (emailValidator.isValid(authToken, null)) {
            userDetails = loadByEmail(authToken)
        }
        val phoneValidator = PhoneNumberValidator()
        if (phoneValidator.isValid(authToken, null)) {
            userDetails = loadByPhone(authToken)
        }
        if (userDetails == null) {
            throw UsernameNotFoundException("Неверные учетные данные пользователя")
        }
        return userDetails
    }

    override fun loadByEmail(email: String): UserAccount? {
        return userDetailsRepository.loadByEmail(email)
    }

    override fun loadByPhone(phone: String): UserAccount? {
        return userDetailsRepository.loadByPhone(phone)
    }
}
