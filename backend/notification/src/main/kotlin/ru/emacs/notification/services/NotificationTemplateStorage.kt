package ru.emacs.notification.services

import org.springframework.context.ApplicationEvent
import ru.emacs.notification.agregators.NotificationTemplate

internal interface NotificationTemplateStorage {
    fun getTemplate(event:ApplicationEvent):List<NotificationTemplate>
}