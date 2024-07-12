package ru.emacs

import org.springframework.context.annotation.Bean
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import ru.emacs.repositories.EmailSettingsRepository
import ru.emacs.repositories.PropertiesRepository
import ru.emacs.repositories.impl.EmailSettingsRepositoryImpl
import ru.emacs.repositories.impl.PropertiesRepositoryImpl
import ru.emacs.services.EmailSettingsService
import ru.emacs.services.PropertiesConverter
import ru.emacs.services.PropertiesService
import ru.emacs.services.SecurityPropertiesService
import ru.emacs.services.impl.EmailSettingsServiceImpl
import ru.emacs.services.impl.PropertiesServiceImpl
import ru.emacs.services.impl.SecurityPropertiesServiceImpl

class ModuleFactory {
    @Bean
    fun cacheConfiguration(): CacheConfiguration {
        return CacheConfiguration()
    }
    @Bean
    internal fun emailSettingsRepository(jdbcTemplate: NamedParameterJdbcTemplate): EmailSettingsRepository {
        return EmailSettingsRepositoryImpl(jdbcTemplate)
    }
    @Bean
    internal fun propertyRepository(jdbcTemplate: NamedParameterJdbcTemplate): PropertiesRepository {
        return PropertiesRepositoryImpl(jdbcTemplate)
    }
    @Bean
    internal fun propertiesConverter(): PropertiesConverter {
        return PropertiesConverter()
    }
    @Bean
    internal fun securityPropertyService(converter: PropertiesConverter,propertiesRepository: PropertiesRepository): SecurityPropertiesService {
        return SecurityPropertiesServiceImpl(converter, propertiesRepository)
    }

    @Bean
    internal fun propertyService(propertiesConverter: PropertiesConverter,propertiesRepository: PropertiesRepository): PropertiesService {
        return PropertiesServiceImpl(propertiesRepository,propertiesConverter)
    }
    @Bean
    internal fun emailSettingsService(emailSettingsRepository: EmailSettingsRepository): EmailSettingsService {
        return EmailSettingsServiceImpl(emailSettingsRepository)
    }
}