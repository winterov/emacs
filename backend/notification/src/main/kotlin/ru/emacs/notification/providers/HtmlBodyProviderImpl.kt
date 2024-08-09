package ru.emacs.notification.providers

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.springframework.stereotype.Component

@Component
internal class HtmlBodyProviderImpl : HtmlBodyProvider {
    override fun provide(htmlBody: String, params: Map<HtmlTag, Map<String,String>>): String {
        val template: Document = Jsoup.parse(htmlBody)
        val document = template.clone()
        params.entries.forEach { entry ->
           when (entry.key) {
               HtmlTag.ID -> entry.value.forEach {
                   processAddText(document,"#${it.key}",it.value)
               }
               HtmlTag.REF -> entry.value.forEach {
                   processSetRef(document,"#${it.key}",it.value)
               }
           }
        }
        return document.toString()
    }

    private fun processAddText(document: Document,query:String, value: String) {
      val elements =   document.select(query)
        elements.forEach {
            it.appendText(value)
        }
    }

    private fun processSetRef(document: Document,query:String, value: String) {
        val elements =   document.select(query)
        elements.forEach {
            it.attr("href",value)
        }
    }
}