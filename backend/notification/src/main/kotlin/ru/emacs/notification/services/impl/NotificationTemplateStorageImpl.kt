package ru.emacs.notification.services.impl

import org.springframework.context.ApplicationEvent
import org.springframework.stereotype.Service
import ru.emacs.notification.services.NotificationTemplateStorage

@Service
internal class NotificationTemplateStorageImpl: NotificationTemplateStorage {

    override fun getTemplate(event: ApplicationEvent) {

    }
}