package ru.emacs.services.impl


import ru.emacs.services.EmailSettingsService
import org.springframework.stereotype.Service
import ru.emacs.models.EmailSettings
import ru.emacs.repositories.EmailSettingsRepository


@Service
internal class EmailSettingsServiceImpl(
    private val emailSettingsRepository: EmailSettingsRepository
): EmailSettingsService {
    override fun getSettings(isEnabled:Boolean): List<EmailSettings> {
        return emailSettingsRepository.getSettings(isEnabled)
    }
}