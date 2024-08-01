package ru.emacs.notification.services.senders

import ru.emacs.notification.agregators.NotificationTemplate

internal data class MessageRecipient(
    val recipient: String,
    val template: NotificationTemplate,
    val params: Map<String, String>,
    val theme: String
)