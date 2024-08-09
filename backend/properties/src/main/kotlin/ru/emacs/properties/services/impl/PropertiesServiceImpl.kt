package ru.emacs.properties.services.impl

import jakarta.el.PropertyNotFoundException
import org.springframework.stereotype.Service
import ru.emacs.properties.repositories.PropertiesRepository
import ru.emacs.properties.services.PropertiesConverter

import ru.emacs.properties.services.PropertiesService

@Service
internal class PropertiesServiceImpl(
    private val propertiesRepository: PropertiesRepository,
    private val propertiesConverter: PropertiesConverter
) : PropertiesService {
    override fun <T> getProperties(t: Class<T>): T {
        val property = propertiesRepository.getProperty(t::class.java) ?: throw PropertyNotFoundException()
        return propertiesConverter.convertFromJson(property.json, t)
    }


}