package ru.emacs.services

import ru.emacs.models.SecurityProperties


interface SecurityPropertiesService {
    fun getSecurityProperty(): SecurityProperties
}