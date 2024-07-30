package ru.emacs.notification.services.senders

import ru.emacs.notification.agregators.NotificationTemplate

internal abstract class MessageSender {
    abstract fun sendMessage(templates: NotificationTemplate)


}