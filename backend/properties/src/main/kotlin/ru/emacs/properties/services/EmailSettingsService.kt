package ru.emacs.properties.services

import ru.emacs.properties.models.EmailSettings


interface EmailSettingsService {
   fun getSettings(isEnabled:Boolean): List<EmailSettings>
}