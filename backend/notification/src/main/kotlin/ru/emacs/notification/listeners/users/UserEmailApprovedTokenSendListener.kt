package ru.emacs.notification.listeners.users


import org.springframework.context.ApplicationListener

import org.springframework.stereotype.Component
import ru.emacs.events.users.UserEmailApprovedTokenSend
import ru.emacs.notification.agregators.NotificationTemplate
import ru.emacs.notification.services.NotificationTemplateStorage
import ru.emacs.notification.services.senders.EMailService

@Component
internal class UserEmailApprovedTokenSendListener (private val notificationTemplateStorage: NotificationTemplateStorage): ApplicationListener<UserEmailApprovedTokenSend> {
    override fun onApplicationEvent(event: UserEmailApprovedTokenSend) {
        val templates = notificationTemplateStorage.getTemplate(event)
        templates.forEach{
                handlerMessage(it,event)
            }
        }
    }
    private  fun handlerMessage(template:NotificationTemplate,event: UserEmailApprovedTokenSend){
        when(template.handlerClass){
            EMailService::class.java ->
        }
    }


}