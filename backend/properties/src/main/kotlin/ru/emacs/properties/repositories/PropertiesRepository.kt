package ru.emacs.properties.repositories

import ru.emacs.properties.models.PropertiesModel


internal interface PropertiesRepository {
    fun getProperty(clazz: Class<*>): PropertiesModel?
}