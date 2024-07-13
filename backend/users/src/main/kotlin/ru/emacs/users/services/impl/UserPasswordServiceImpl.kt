package ru.emacs.users.services.impl



import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.emacs.properties.services.SecurityPropertiesService
import ru.emacs.users.agregators.EUserStatus
import ru.emacs.users.agregators.UserAccount
import ru.emacs.users.repositories.ChangePasswordTokenRepository
import ru.emacs.users.repositories.UserPasswordRepository
import ru.emacs.users.services.UserAccountService
import ru.emacs.users.services.UserPasswordService
import ru.emacs.users.utils.ApprovedTokenUtils.generateApprovedToken
import ru.emacs.users.utils.PBFDK2Encoder
import ru.emacs.validators.PasswordValidator
import java.nio.file.attribute.UserPrincipal
import java.time.LocalDateTime


@Service
internal class UserPasswordServiceImpl @Autowired constructor(
    private val bCryptPasswordEncoder: PBFDK2Encoder,
    private val userAccountDetailsService: UserAccountService,
    private val changePasswordTokenRepository: ChangePasswordTokenRepository,
    private val userPasswordRepository: UserPasswordRepository,
    private val securityPropertiesService: SecurityPropertiesService,
    private val messageSource: MessageSource
) : UserPasswordService {
    @Transactional
    override fun createChangePasswordToken(email: String): String? {
        val securityProperties = securityPropertiesService.getSecurityProperty()
        val userAccount = userAccountDetailsService.loadByEmail(email)
        var errorMessage = checkUserAccount(userAccount,email)
        if(errorMessage!=null) return errorMessage
        val pauseBetweenNextToken = LocalDateTime.now()
            .plusSeconds(securityProperties.restorePasswordTokenProperty.pauseBetweenNextTokenGenerate)
        val token = changePasswordTokenRepository.getTokenByUserEmail(email)
        if (token != null && token.createdAt!!.isBefore(pauseBetweenNextToken)) {
            errorMessage=messageSource.getMessage("changePassword.tooFrequentRequests",null, LocaleContextHolder.getLocale())
            return errorMessage
        }
        val expireTokenTime = LocalDateTime.now().plus(
            securityProperties.restorePasswordTokenProperty.restorePasswordTokenLifetime,
            securityProperties.restorePasswordTokenProperty.unit
        )
        val newToken: String =
            generateApprovedToken(securityProperties.restorePasswordTokenProperty.restorePasswordTokenLength)
        changePasswordTokenRepository.saveChangePasswordToken(userAccount!!.id!!, expireTokenTime, newToken)
        //val globalProperties = GlobalProperties()
        // val url = globalProperties.host + "password/restore?email=" + email + "&token=" + newToken
        return null
    }
    @Transactional
    override fun restorePassword(email: String, password: String, token: String): String? {
        val userAccount = userAccountDetailsService.loadByEmail(email)
        var errorMessage = checkUserAccount(userAccount, email)
        if(errorMessage!=null) return errorMessage
        val passwordChangeToken= changePasswordTokenRepository.getTokenByUserEmail(email)
        if (passwordChangeToken == null||passwordChangeToken.token != token) {
            errorMessage=messageSource.getMessage("changePassword.tokenIsDeprecated",null, LocaleContextHolder.getLocale())
            return errorMessage
        }
       return changePassword(userAccount!!,password)
    }
    @Transactional
    override fun changePasswordAuthenticatedUser(principal: UserPrincipal, password: String):String?{
        val userAccount = userAccountDetailsService.loadByEmail(principal.name)
        val errorMessage = checkUserAccount(userAccount, principal.name)
        if(errorMessage!=null) return errorMessage
        return changePassword(userAccount!!,password)
    }

    private fun changePassword(userAccount: UserAccount, password: String):String?{
        val securityProperties = securityPropertiesService.getSecurityProperty()
        val passwordValidator = PasswordValidator(securityPropertiesService)
        val errorMessage:String?
        if (!passwordValidator.isValid(password, null)) {
            errorMessage=messageSource.getMessage("changePassword.notValid",null, LocaleContextHolder.getLocale())
            return errorMessage
        }
        if (bCryptPasswordEncoder.matches(password, userAccount.password)) {
            errorMessage=messageSource.getMessage("changePassword.newPasswordMatchOldPassword",null, LocaleContextHolder.getLocale())
            return errorMessage
        }
        val passwordHash = bCryptPasswordEncoder.encode(password)
        val credentialExpired = LocalDateTime.now().plus(
            securityProperties.userPasswordStrength.passwordExpired,
            securityProperties.userPasswordStrength.unit
        )
        userPasswordRepository.updatePassword(userAccount.id!!, passwordHash, credentialExpired, LocalDateTime.now())
        changePasswordTokenRepository.deleteUsedToken(userAccount.id!!)
        return null
    }
    private fun checkUserAccount(userAccount: UserAccount?, email: String):String?{
        var errorMessage: String?=null
        if(userAccount==null){
            val params = arrayOf(email)
            errorMessage=messageSource.getMessage("user.notFoundByEmail",params, LocaleContextHolder.getLocale())
            return errorMessage
        }
        if (!(userAccount.status == EUserStatus.NEW_USER || userAccount.status == EUserStatus.ACTIVE)) {
            errorMessage=messageSource.getMessage("user.accountIsBlocked",null, LocaleContextHolder.getLocale())
        }
        return errorMessage
    }

}
