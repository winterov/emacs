package ru.emacs.notification.services.senders.impl

import jakarta.annotation.PostConstruct
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.stereotype.Service
import ru.emacs.properties.models.EEmailType
import ru.emacs.properties.models.EmailSettings
import ru.emacs.properties.services.EmailSettingsService


@Service
class EMailService(private val emailSettingsService: EmailSettingsService){

    private val mailSenders = HashMap<EEmailType, JavaMailSenderImpl>(2)
    init {
        EEmailType.entries.forEach {
            mailSenders[it]=JavaMailSenderImpl()
        }
    }
    @PostConstruct
    fun init() {
       val settings = emailSettingsService.getSettings(true)
       settings.forEach{updateMailProperty(it)}
    }
    fun updateMailProperty(emailProperty: EmailSettings) {
        val mailSender = mailSenders[emailProperty.type]!!
        mailSender.host = emailProperty.smtpServer.host
        mailSender.port = emailProperty.smtpServer.portTLS!!
        mailSender.username = emailProperty.email
        mailSender.password = emailProperty.password
        val props = mailSender.javaMailProperties
        props["mail.transport.protocol"] = emailProperty.smtpServer.protocol
        props["mail.smtp.auth"] = emailProperty.smtpServer.requireAuth.toString()
        props["mail.smtp.starttls.enable"] =true
    }
}
