package ru.emacs.notification.services.senders

import org.springframework.mail.javamail.JavaMailSender

import ru.emacs.properties.models.EEmailType
import ru.emacs.properties.models.EmailSettings

interface EMailService {
    fun updateMailProperty(emailProperty: EmailSettings)
    fun getEmailSender(email: EEmailType): JavaMailSender
}