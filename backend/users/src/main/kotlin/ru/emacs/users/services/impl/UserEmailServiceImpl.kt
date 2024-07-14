package ru.emacs.users.services.impl


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.emacs.properties.services.SecurityPropertiesService
import ru.emacs.users.agregators.EUserStatus
import ru.emacs.users.agregators.EmailApprovedToken
import ru.emacs.users.agregators.UserAccount
import ru.emacs.users.repositories.UserEmailRepository
import ru.emacs.users.services.UserEmailService
import ru.emacs.users.utils.ApprovedTokenUtils
import java.time.LocalDateTime


@Service
internal class UserEmailServiceImpl @Autowired constructor(
    private val userEmailRepository: UserEmailRepository,
    private val userDetailsService: UserDetailsService,
    private val securityPropertiesService: SecurityPropertiesService,
) : UserEmailService {
    @Transactional
    override fun generateVerifiedEmailToken(email: String): List<String> {
        val userAccount = userDetailsService.loadUserByUsername(email) as UserAccount?
        val errorMessage: MutableList<String> = ArrayList()
        if (userAccount == null) {
            errorMessage.add("Неверные учетные данные")
            return errorMessage
        }
        if (java.lang.Boolean.TRUE == userAccount.isEmailVerified) {
            errorMessage.add("Email ${userAccount.email} уже подтвержден")
        }
        if (userAccount.status!! == EUserStatus.NEW_USER
            || userAccount.status!! == EUserStatus.ACTIVE
        ) {
            errorMessage.add("Ваш аккаунт заблокирован. Обратитесь к администратору")
            return errorMessage
        }
        val emailApprovedToken = userEmailRepository.getVerifiedToken(userAccount.id!!)
        val securityProperties = securityPropertiesService.getSecurityProperty()
        if (emailApprovedToken != null) {
            val nextTokenTime = emailApprovedToken.createdAt.plus(
                securityProperties.approvedEmailProperty.pauseBetweenNextTokenGenerate,
                securityProperties.approvedEmailProperty.unit
            )
            if (nextTokenTime.isAfter(LocalDateTime.now())) {
                errorMessage.add("Слишком частые запросы")
            }
        }
        if (errorMessage.isNotEmpty()) {
            return errorMessage
        }
        val token: String =
            ApprovedTokenUtils.generateApprovedToken(securityProperties.approvedEmailProperty.approvedEmailTokenLength)
        val expired = LocalDateTime.now().plus(
            securityProperties.approvedEmailProperty.approvedEmailTokenLifetime,
            securityProperties.approvedEmailProperty.unit
        )
        userEmailRepository.saveVerifiedEmailToken(userAccount.id!!, token, expired)
        val newToken = EmailApprovedToken(userAccount.id!!, token, LocalDateTime.now(), expired)
        /*eventMulticaster.multicastEvent(UserEmailApprovedTokenEvent(userAccount))*/
        return errorMessage
    }

    @Transactional
    override fun approvedUserEmail(email: String, token: String): MutableList<String> {
        TODO()
        /*val userAccount = userEmailRepository.getVerifiedToken(email)
        val errorMessage: MutableList<String> = ArrayList()
        if (userAccount == null) {
            errorMessage.add("Неверные учетные данные")
            return errorMessage
        }
        val emailApprovedToken = userEmailRepository.getVerifiedToken(userAccount.id!!)
        if (emailApprovedToken == null || emailApprovedToken.token != token) {
            errorMessage.add("Невалидный токен подтверждения")
        }
        if (java.lang.Boolean.TRUE == userAccount.getIsEmailVerified()) {
            errorMessage.add("Email $email уже подтвержден")
        }
        if (!(userAccount.getStatus().equals(EUserStatus.NEW_USER)
                    || userAccount.getStatus().equals(EUserStatus.ACTIVE))
        ) {
            errorMessage.add("Ваш аккаунт заблокирован. Обратитесь к администратору")
        }
        if (!errorMessage.isEmpty()) {
            return errorMessage
        }
        userEmailRepository.updateUserEmailStatusByUserId(userAccount.getId(), true)
        userEmailRepository.deleteUsedEmailApprovedTokenByUserId(userAccount.getId())
        eventMulticaster.multicastEvent(UserAccountChangeEvent(email))
        return errorMessage*/
    }

    @Transactional
    override fun emailBusyCheck(email: String): Boolean {
        return userEmailRepository.countOfUsageEmail(email) > 0
    }
}
