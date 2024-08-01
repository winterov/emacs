package ru.emacs.notification.agregators

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("notification_template")
data class NotificationTemplate(
    @field:Id val id:Long,
    val eventClass:Class<*>,
    val handlerClass:Class<*>,
    val template:String,
    val isEnabled:Boolean,
    val createdAt:LocalDateTime,
)
