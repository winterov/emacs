package ru.emacs.properties.services

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.ObjectMapper
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component


@Component
internal class PropertiesConverter {
    private val logger: Logger = LoggerFactory.getLogger(PropertiesConverter::class.java)
    private val objectMapper = ObjectMapper()
    fun convertToJson(property: Any): String? {
        var propertyString: String? = null
        try {
            propertyString = objectMapper.writeValueAsString(property)
        } catch (e: JsonProcessingException) {
            logger.error(e.message + ":::" + property.toString())
        }
        return propertyString
    }


    fun <T> convertFromJson(s: String, tClass: Class<T>): T {
        var property: T?=null
        try {
            property = objectMapper.readValue(s, tClass) as T
        } catch (e: JsonProcessingException) {
            logger.error(e.message + ":::" + s)
        }
        return property!!
    }

}
