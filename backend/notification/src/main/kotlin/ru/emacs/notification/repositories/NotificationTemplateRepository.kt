package ru.emacs.notification.repositories

import org.springframework.data.repository.CrudRepository
import ru.emacs.notification.agregators.NotificationTemplate

internal interface NotificationTemplateRepository: CrudRepository<NotificationTemplate,Long> {
    fun findByEventClassAndEnabled(eventClass:Class<*>,isEnabled:Boolean): List<NotificationTemplate>
}