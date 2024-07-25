package ru.emacs.notification.services

internal interface HtmlBodyProvider {
  fun provide(htmlBody: String, params: Map<String,String>): String
}