package ru.emacs.services

import ru.emacs.models.EmailSettings


interface EmailSettingsService {
   fun getSettings(isEnabled:Boolean): List<EmailSettings>
}