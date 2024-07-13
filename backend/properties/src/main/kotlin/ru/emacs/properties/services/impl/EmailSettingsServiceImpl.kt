package ru.emacs.properties.services.impl


import org.springframework.stereotype.Service
import ru.emacs.properties.models.EmailSettings
import ru.emacs.properties.repositories.EmailSettingsRepository
import ru.emacs.properties.services.EmailSettingsService


@Service
internal class EmailSettingsServiceImpl(
    private val emailSettingsRepository: EmailSettingsRepository
): EmailSettingsService {
    override fun getSettings(isEnabled:Boolean): List<EmailSettings> {
        return emailSettingsRepository.getSettings(isEnabled)
    }
}