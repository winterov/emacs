package ru.emacs.notification.listeners.users


import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.mail.javamail.MimeMessageHelper

import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import ru.emacs.events.users.UserEmailApprovedTokenSend
import ru.emacs.notification.providers.HtmlBodyProvider
import ru.emacs.notification.providers.HtmlTag
import ru.emacs.notification.services.NotificationTemplateStorage
import ru.emacs.notification.services.senders.EMailService
import ru.emacs.properties.models.EEmailType
import ru.emacs.properties.models.GlobalProperties
import ru.emacs.properties.services.PropertiesService
import java.util.*


@Component
internal class UserEmailApprovedTokenSendListener @Autowired constructor(
    private val htmlBodyProvider: HtmlBodyProvider,
    private val eMailService: EMailService,
    private val propertiesService: PropertiesService,
    private val notificationTemplateStorage: NotificationTemplateStorage
) : ApplicationListener<UserEmailApprovedTokenSend> {

    private val log = LoggerFactory.getLogger(UserEmailApprovedTokenSendListener::class.java)


    override fun onApplicationEvent(event: UserEmailApprovedTokenSend) {
        val templates = notificationTemplateStorage.getEnabledTemplate(event)
        if (templates.isEmpty() || templates.size > 1) {
            val message = "Количество шаблонов не равно одному"
            log.error(message)
            return
        }
        val template = templates[0]
        val params = createParams(event)
        val message = htmlBodyProvider.provide(template.template, params)
        val sender = eMailService.getEmailSender(EEmailType.ADMIN_SENDER)
        val mimeMessage = sender.createMimeMessage()
        mimeMessage.setContent(message,"text/html; charset=utf-8")
        val helper = MimeMessageHelper(mimeMessage)
        helper.setTo(event.user.email)
        helper.setSubject("Подтверждение почты")
        sender.send(mimeMessage)
    }

    private fun createParams(event: UserEmailApprovedTokenSend): MutableMap<HtmlTag, Map<String, String>> {
        val params: MutableMap<HtmlTag, Map<String, String>> = EnumMap(HtmlTag::class.java)
        val globalProperties = propertiesService.getProperties(GlobalProperties::class.java)
        params[HtmlTag.REF] = mapOf(Pair("emailApprTag", createApprovedRef(globalProperties.host, event)))
        return params
    }

    private fun createApprovedRef(host: String, event: UserEmailApprovedTokenSend): String {
        val url = UriComponentsBuilder
            .fromHttpUrl(host)
            .path("")
            .queryParam("email", event.user.email)
            .queryParam("token", event.user.token)
            .build()
            .encode()
            .toUriString()
        log.debug("url: $url")
        return url
    }

}
