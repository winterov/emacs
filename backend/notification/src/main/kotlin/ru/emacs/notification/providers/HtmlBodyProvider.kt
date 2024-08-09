package ru.emacs.notification.providers

internal interface HtmlBodyProvider {
  fun provide(htmlBody: String, params: Map<HtmlTag,Map<String,String>>): String
}