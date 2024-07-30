package ru.emacs.notification.providers

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

@Component
internal class HtmlBodyProviderImpl: HtmlBodyProvider {
    override fun provide(htmlBody: String, params: Map<String, String>): String {
        val template: Document = Jsoup.parse(htmlBody)
        val document = template.clone()
        return document.toString()
    }
}