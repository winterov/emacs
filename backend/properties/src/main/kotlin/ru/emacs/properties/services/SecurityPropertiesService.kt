package ru.emacs.properties.services

import ru.emacs.properties.models.SecurityProperties


interface SecurityPropertiesService {
    fun getSecurityProperty(): SecurityProperties
}