package ru.emacs.properties.models

enum class EEmailType {
    ADMIN_SENDER, MANAGER_TYPE;

    val title: String
        get() {
            val title: String = when (this) {
                ADMIN_SENDER -> "Административный"
                MANAGER_TYPE -> "Управленческая"
            }
            return title
        }
}
