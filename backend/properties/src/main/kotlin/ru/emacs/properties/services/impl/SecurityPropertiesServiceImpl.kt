package ru.emacs.properties.services.impl



import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import ru.emacs.properties.CacheConfiguration
import ru.emacs.properties.models.SecurityProperties
import ru.emacs.properties.repositories.PropertiesRepository
import ru.emacs.properties.services.PropertiesConverter
import ru.emacs.properties.services.SecurityPropertiesService

@Service
internal open class SecurityPropertiesServiceImpl(private val converter: PropertiesConverter,
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