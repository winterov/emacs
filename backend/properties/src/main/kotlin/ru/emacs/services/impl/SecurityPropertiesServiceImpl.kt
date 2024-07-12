package ru.emacs.services.impl



import ru.emacs.services.PropertiesConverter
import ru.emacs.services.SecurityPropertiesService
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.emacs.CacheConfiguration
import ru.emacs.models.SecurityProperties
import ru.emacs.repositories.PropertiesRepository


@Service
internal class SecurityPropertiesServiceImpl(private val converter: PropertiesConverter,
                                             private val propertiesRepository: PropertiesRepository
) : SecurityPropertiesService {

    @Cacheable(CacheConfiguration.SECURITY_PROPERTIES_CACHE)
    override fun getSecurityProperty(): SecurityProperties {
        val model = propertiesRepository.getProperty(SecurityProperties::class.java) ?: return defaultSecurityProperty()
        val securityProperties= converter.convertFromJson(model.json,model.clazz) as SecurityProperties
        return securityProperties
    }

    private fun defaultSecurityProperty(): SecurityProperties {
        val securityProperties = SecurityProperties()
        securityProperties.jwtProperties = SecurityProperties.JwtProperties()
        securityProperties.approvedEmailProperty = SecurityProperties.ApprovedEmailProperty()
        securityProperties.restorePasswordTokenProperty = SecurityProperties.RestorePasswordTokenProperty()
        securityProperties.userPasswordStrength = SecurityProperties.UserPasswordStrength()
        securityProperties.passwordProperties = SecurityProperties.PasswordProperties()
        return securityProperties
    }
}