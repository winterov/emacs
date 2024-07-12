package ru.emacs.repositories

import ru.emacs.models.PropertiesModel


internal interface PropertiesRepository {
    fun getProperty(clazz: Class<*>): PropertiesModel?
}