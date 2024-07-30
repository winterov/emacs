package ru.emacs.notification.services.senders.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import ru.emacs.notification.agregators.NotificationTemplate
import ru.emacs.notification.services.senders.MessageSender


@Component
internal class MailMessageSenderImpl @Autowired constructor(private val emailSender: JavaMailSender): MessageSender() {
    override fun sendMessage(templates: NotificationTemplate) {

    }


}