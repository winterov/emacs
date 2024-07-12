package ru.emacs.services.impl

import jakarta.el.PropertyNotFoundException
import org.springframework.stereotype.Service
import ru.emacs.repositories.PropertiesRepository
import ru.emacs.services.PropertiesConverter

import ru.emacs.services.PropertiesService

@Service
internal class PropertiesServiceImpl(
    private val propertiesRepository: PropertiesRepository,
    private val propertiesConverter: PropertiesConverter
): PropertiesService {
    override fun <T> getProperties(t:T): T {
     val property = propertiesRepository.getProperty(t!!::class.java) ?: throw PropertyNotFoundException()
        return propertiesConverter.convertFromJson(property.json, t!!::class.java)
    }


}