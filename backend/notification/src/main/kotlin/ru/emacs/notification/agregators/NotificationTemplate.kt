package ru.emacs.notification.agregators

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import ru.emacs.notification.services.SenderType
import java.time.LocalDateTime

@Table("notification_template")
data class NotificationTemplate(
    @field:Id val id:Long,
    val eventClass:Class<*>,
    val handler: SenderType,
    val template:String,
    @field:Column("is_enabled")
    val actual:Boolean,
    val createdAt:LocalDateTime,
)
