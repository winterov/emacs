package ru.emacs.users.services.impl



import jakarta.validation.Validator
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.event.ApplicationEventMulticaster
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.emacs.dtos.AppResponseErrorDto
import ru.emacs.properties.services.SecurityPropertiesService
import ru.emacs.users.agregators.EUserStatus
import ru.emacs.users.agregators.UserAccount
import ru.emacs.users.dtos.profile.request.UserRegistrationRequestDto
import ru.emacs.users.repositories.UserRegistrationRepository
import ru.emacs.users.services.RoleService
import ru.emacs.users.services.UserEmailService
import ru.emacs.users.services.UserRegistrationService
import ru.emacs.users.utils.PBFDK2Encoder
import ru.emacs.validators.validateDto
import java.time.LocalDateTime


@Service
internal class UserRegistrationServiceImpl @Autowired constructor(
    private val pbfdk2Encoder: PBFDK2Encoder,
    private val userEmailService: UserEmailService,
    private val userRegistrationRepository: UserRegistrationRepository,
    private val roleService: RoleService,
    private val securityPropertiesService: SecurityPropertiesService,
    private val validator: Validator,
    private val messageSource: MessageSource,
    private val simpleApplicationEventMulticaster: ApplicationEventMulticaster
) : UserRegistrationService {

    override fun createNewUserAccount(dto: UserRegistrationRequestDto): Pair<Any?, HttpStatus> {
        val errors = validateDto(validator,dto)
        if (errors.isNotEmpty()) {
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST, errors)
            return Pair(errorDto,HttpStatus.BAD_REQUEST)
        }
        val emailBusy = userEmailService.emailBusyCheck(dto.email!!)
        if(emailBusy.second != HttpStatus.OK) {
            return emailBusy
        }
        val passwordHash = pbfdk2Encoder.encode(dto.password!!)
        val createdAt = LocalDateTime.now()
        val credentialExpired = createdAt.plus(
            securityPropertiesService.getSecurityProperty().userPasswordStrength.passwordExpired,
            securityPropertiesService.getSecurityProperty().userPasswordStrength.unit
        )
        val newUserId = userRegistrationRepository.saveNewUser(
            dto.email,
            dto.phone,
            passwordHash,
            credentialExpired,
            EUserStatus.NEW_USER,
            createdAt
        )
        roleService.setDefaultRoleForNewUser(newUserId!!)
        val userAccount = UserAccount()
        userAccount.id = newUserId
        userAccount.email = dto.email
        userAccount.phone = dto.phone
        userAccount.status = EUserStatus.NEW_USER
        userAccount.credentialExpiredTime = credentialExpired
        userAccount.createdAt = createdAt
        userEmailService.generateVerifiedEmailTokenAndSend(dto.email)
        return Pair(null,HttpStatus.OK)
    }
}
