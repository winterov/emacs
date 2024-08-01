package ru.emacs.notification.services.senders.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Component
import ru.emacs.notification.agregators.NotificationTemplate
import ru.emacs.notification.providers.HtmlBodyProvider
import ru.emacs.notification.services.senders.EMailService
import ru.emacs.notification.services.senders.MessageRecipient
import ru.emacs.notification.services.senders.MessageSender
import ru.emacs.properties.models.EEmailType


@Component
internal class MailMessageSenderImpl @Autowired constructor(
    private val eMailService: EMailService,
    private val htmlBodyProvider: HtmlBodyProvider,
) : MessageSender() {

    override fun sendMessage(templates: NotificationTemplate, recipient: MessageRecipient) {
        val sender = eMailService.getEmailSender(EEmailType.ADMIN_SENDER)
        val message = sender.createMimeMessage()
        message.setContent(htmlBodyProvider.provide(recipient.template.template,recipient.params),"text/html; charset=utf-8")
        val  helper = MimeMessageHelper(message, false)
        helper.setSubject(recipient.theme)
        helper.setTo(recipient.recipient)
        sender.send(message)
    }


}