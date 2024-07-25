package ru.emacs.notification.services

import org.springframework.context.ApplicationEvent

internal interface NotificationTemplateStorage {
    fun getTemplate(event:ApplicationEvent)
}