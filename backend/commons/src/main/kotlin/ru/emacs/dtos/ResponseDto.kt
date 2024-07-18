package ru.emacs.dtos

import org.springframework.http.HttpStatus

data class ResponseDto<T>(
    val statusCode: HttpStatus,
    val data: T? = null,
    val errors: List<String>
)