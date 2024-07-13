package ru.emacs.properties.repositories

import ru.emacs.properties.models.EmailSettings


internal interface EmailSettingsRepository {
    fun getSettings(isEnabled:Boolean): List<EmailSettings>
}