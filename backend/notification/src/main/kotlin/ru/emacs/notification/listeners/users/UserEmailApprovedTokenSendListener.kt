package ru.emacs.notification.listeners.users

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Component
import ru.emacs.events.users.UserEmailApprovedTokenSend
@Component
class UserEmailApprovedTokenSendListener @Autowired constructor(private val emailSender: JavaMailSender): ApplicationListener<UserEmailApprovedTokenSend> {


    override fun onApplicationEvent(event: UserEmailApprovedTokenSend) {

    }
}