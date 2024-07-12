package ru.emacs.dtos

import org.springframework.http.HttpStatusCode
data class AppResponseErrorDto(val statusCode: HttpStatusCode, val  message: List<String>){
    constructor(statusCode: HttpStatusCode, message: String) : this(statusCode, listOf(message))
}

