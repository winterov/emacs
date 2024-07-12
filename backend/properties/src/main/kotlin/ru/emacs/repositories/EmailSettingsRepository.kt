package ru.emacs.repositories

import ru.emacs.models.EmailSettings


internal interface EmailSettingsRepository {
    fun getSettings(isEnabled:Boolean): List<EmailSettings>
}