package ru.emacs.notification.listeners.users


import org.springframework.context.ApplicationListener

import org.springframework.stereotype.Component
import ru.emacs.events.users.UserEmailApprovedTokenSend
@Component
class UserEmailApprovedTokenSendListener : ApplicationListener<UserEmailApprovedTokenSend> {


    override fun onApplicationEvent(event: UserEmailApprovedTokenSend) {

    }
}