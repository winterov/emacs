package ru.emacs.services.impl



import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import ru.emacs.agregators.EUserStatus
import ru.emacs.agregators.UserAccount
import ru.emacs.models.SecurityProperties
import ru.emacs.repositories.UserRegistrationRepository
import ru.emacs.services.RoleService
import ru.emacs.services.SecurityPropertiesService
import ru.emacs.services.UserEmailService
import ru.emacs.services.UserRegistrationService
import ru.emacs.utils.PBFDK2Encoder
import java.time.LocalDateTime


@Service
internal class UserRegistrationServiceImpl @Autowired constructor(
    private val pbfdk2Encoder: PBFDK2Encoder,
    private val userEmailService: UserEmailService,
    private val userRegistrationRepository: UserRegistrationRepository,
    private val roleService: RoleService,
    securityPropertiesService: SecurityPropertiesService
) : UserRegistrationService {
    private var securityProperties: SecurityProperties = securityPropertiesService.getSecurityProperty()
    override fun createNewUserAccount(
        email: String,
        phone: String?,
        password: String,
        name: String,
        surname: String,
        lastname: String?
    ) {
        val passwordHash = pbfdk2Encoder.encode(password)
        val createdAt = LocalDateTime.now()
        val credentialExpired = createdAt.plus(
            securityProperties.userPasswordStrength.passwordExpired,
            securityProperties.userPasswordStrength.unit
        )
        val newUserId = userRegistrationRepository.saveNewUser(
            email,
            phone,
            passwordHash,
            credentialExpired,
            EUserStatus.NEW_USER,
            createdAt
        )
        roleService.setDefaultRoleForNewUser(newUserId!!)
        val userAccount = UserAccount()
        userAccount.id = newUserId
        userAccount.email = email
        userAccount.phone = phone
        userAccount.status = EUserStatus.NEW_USER
        userAccount.credentialExpiredTime = credentialExpired
        userAccount.createdAt = createdAt
        userEmailService.generateVerifiedEmailToken(email)
    }
}
