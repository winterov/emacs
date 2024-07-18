package ru.emacs.exception

import org.postgresql.util.ServerErrorMessage
import org.springframework.stereotype.Component

@Component
class PSQLErrorsTranslator {
    private val translator: MutableMap<String, String> = HashMap()
    init {
        translator["23505"] = "Объект с такими уникальными данными существует"
    }

    fun getMessage(errorMessage: ServerErrorMessage): String {
        val sqlState: String? = errorMessage.sqlState
        val message = translator[sqlState] ?: return errorMessage.message ?:"Что то пошло не так"
        return message
    }
}
