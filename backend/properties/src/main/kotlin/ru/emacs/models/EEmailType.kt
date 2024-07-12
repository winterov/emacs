package ru.emacs.models

enum class EEmailType {
    ADMIN_SENDER, REGISTER_SENDER, MANAGER_TYPE;

    val title: String
        get() {
            val title: String = when (this) {
                ADMIN_SENDER -> "Административный"
                REGISTER_SENDER -> "Регистратура"
                MANAGER_TYPE -> "Управленческая"
            }
            return title
        }
}
