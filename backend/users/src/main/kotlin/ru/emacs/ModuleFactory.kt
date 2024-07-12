package ru.emacs

import jakarta.validation.Validator
import org.springframework.context.MessageSource
import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.security.core.userdetails.UserDetailsService
import ru.emacs.controllers.AuthenticationControllerApiV1
import ru.emacs.controllers.UserEmailControllerApiV1
import ru.emacs.controllers.UserRegistrationControllerApiV1
import ru.emacs.repositories.*
import ru.emacs.repositories.ChangePasswordTokenRepository
import ru.emacs.repositories.RefreshTokenRepository
import ru.emacs.repositories.RoleRepository
import ru.emacs.repositories.UserDetailsRepository
import ru.emacs.repositories.UserEmailRepository
import ru.emacs.repositories.impl.*
import ru.emacs.repositories.impl.ChangePasswordTokenRepositoryImpl
import ru.emacs.repositories.impl.RefreshTokenRepositoryImpl
import ru.emacs.repositories.impl.RoleRepositoryImpl
import ru.emacs.repositories.impl.UserDetailsRepositoryImpl
import ru.emacs.services.*
import ru.emacs.services.RefreshTokenService
import ru.emacs.services.UserAccountService
import ru.emacs.services.impl.*
import ru.emacs.services.impl.AuthenticationServiceImpl
import ru.emacs.services.impl.RefreshTokenServiceImpl
import ru.emacs.services.impl.RolesServiceImpl
import ru.emacs.services.impl.UserAccountDetailsService
import ru.emacs.users.services.impl.UserPasswordServiceImpl
import ru.emacs.utils.JwtUtils
import ru.emacs.utils.PBFDK2Encoder

class ModuleFactory {
    @Bean
    internal fun jwtRequestFilter(jwtUtils: JwtUtils): JwtRequestFilter {
        return JwtRequestFilter(jwtUtils)
    }

    @Bean
    internal fun securityConfiguration(jwtRequestFilter: JwtRequestFilter, userAccountDetailsService: UserAccountService): SecurityConfiguration {
        return SecurityConfiguration(jwtRequestFilter, userAccountDetailsService)
    }
    //utils
    @Bean
    internal fun jwtUtils(securityPropertiesService: SecurityPropertiesService): JwtUtils {
        return JwtUtils(securityPropertiesService)
    }
    @Bean
    internal fun getPBFDK2Encoder(securityPropertiesService: SecurityPropertiesService): PBFDK2Encoder {
        return PBFDK2Encoder(securityPropertiesService)
    }
    //repository
    @Bean
    internal fun changePasswordTokenRepository(jdbcTemplate: NamedParameterJdbcTemplate): ChangePasswordTokenRepository{
        return ChangePasswordTokenRepositoryImpl(jdbcTemplate)
    }
    @Bean
    internal fun refreshTokenRepository(jdbcTemplate: NamedParameterJdbcTemplate): RefreshTokenRepository{
        return RefreshTokenRepositoryImpl(jdbcTemplate)
    }
    @Bean
    internal fun roleRepository(jdbcTemplate: NamedParameterJdbcTemplate): RoleRepository{
        return RoleRepositoryImpl(jdbcTemplate)
    }
    @Bean
    internal fun userDetailsRepository(jdbcTemplate: NamedParameterJdbcTemplate): UserDetailsRepository {
        return UserDetailsRepositoryImpl(jdbcTemplate)
    }
    @Bean
    internal fun userEmailRepository(jdbcTemplate: NamedParameterJdbcTemplate): UserEmailRepository {
        return UserEmailRepositoryImpl(jdbcTemplate)
    }
    @Bean
    internal fun userPasswordRepository(jdbcTemplate: NamedParameterJdbcTemplate): UserPasswordRepository {
        return UserPasswordRepositoryImpl(jdbcTemplate)
    }
    @Bean
    internal fun userRegistrationRepository(jdbcTemplate: NamedParameterJdbcTemplate): UserRegistrationRepository {
        return UserRegistrationRepositoryImpl(jdbcTemplate)
    }
    //service
    @Bean
    internal fun authenticationService(detailsService: UserAccountDetailsService,
                                       refreshTokenService: RefreshTokenService,
                                       encoder: PBFDK2Encoder,
                                       jwtUtil: JwtUtils,
                                       securityPropertiesService: SecurityPropertiesService): AuthenticationService {
        return AuthenticationServiceImpl(detailsService,refreshTokenService,encoder,jwtUtil, securityPropertiesService)
    }
    @Bean
    internal fun refreshTokenService( refreshTokenRepository: RefreshTokenRepository,
                                      jwtUtil: JwtUtils,
                                      securityPropertiesService: SecurityPropertiesService): RefreshTokenService{
        return RefreshTokenServiceImpl(refreshTokenRepository,jwtUtil,securityPropertiesService)
    }

    @Bean
    internal fun roleService(roleRepository: RoleRepository): RoleService {
        return RolesServiceImpl(roleRepository)
    }
    @Bean
    internal fun userAccountService(userDetailsRepository: UserDetailsRepository): UserAccountService{
        return UserAccountDetailsService(userDetailsRepository)
    }
    @Bean
    internal fun userEmailService(userEmailRepository: UserEmailRepository,
                                  userDetailsService: UserDetailsService,
                                  securityPropertiesService: SecurityPropertiesService,): UserEmailService{
        return UserEmailServiceImpl(userEmailRepository,userDetailsService,securityPropertiesService)
    }
    @Bean
    internal fun userPasswordService(bCryptPasswordEncoder: PBFDK2Encoder,
                                     userAccountDetailsService: UserAccountService,
                                     changePasswordTokenRepository: ChangePasswordTokenRepository,
                                     userPasswordRepository: UserPasswordRepository,
                                     securityPropertiesService: SecurityPropertiesService,
                                     messageSource: MessageSource
    ): UserPasswordService {
        return UserPasswordServiceImpl(bCryptPasswordEncoder,userAccountDetailsService
            ,changePasswordTokenRepository,userPasswordRepository,securityPropertiesService,messageSource)
    }
    @Bean
    internal fun roleService(pbfdk2Encoder: PBFDK2Encoder,
                             userEmailService: UserEmailService,
                             userRegistrationRepository: UserRegistrationRepository,
                             roleService: RoleService,
                             securityPropertiesService: SecurityPropertiesService): UserRegistrationService {
        return UserRegistrationServiceImpl(pbfdk2Encoder,userEmailService,
            userRegistrationRepository,roleService,securityPropertiesService)
    }
    //controllers
    @Bean
    internal fun authenticationControllerApiV1(authenticationService: AuthenticationService,
                                               validator: Validator,
                                               messageSource: MessageSource): AuthenticationControllerApiV1 {
        return AuthenticationControllerApiV1(authenticationService,validator,messageSource)
    }
    @Bean
    internal fun userEmailControllerApiV1(validator: Validator,
                                          userEmailService: UserEmailService): UserEmailControllerApiV1{
        return UserEmailControllerApiV1(validator,userEmailService)
    }
    @Bean
    internal fun userRegistrationControllerApiV1(validator: Validator,
                                                 userRegistrationService: UserRegistrationService): UserRegistrationControllerApiV1 {
        return UserRegistrationControllerApiV1(validator,userRegistrationService)
    }

}