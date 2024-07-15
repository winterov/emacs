package ru.emacs.validators

import jakarta.validation.Validator
fun validateDto(validator: Validator, dto: Any):List<String> {
    val violations = validator.validate(dto)
    val messages=ArrayList<String>()
    if (violations.isNotEmpty()) {
        violations.forEach { messages.add(it.message) }
    }
    return messages
}