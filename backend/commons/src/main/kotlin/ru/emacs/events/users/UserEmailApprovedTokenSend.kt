package ru.emacs.events.users

import org.springframework.context.ApplicationEvent

data class UserEmailApprovedTokenSend(val user:UserAccountWithToken): ApplicationEvent(user)