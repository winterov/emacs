package ru.emacs.responses


import ru.emacs.models.SecurityProperties
import java.time.temporal.ChronoUnit


class PasswordStrengthResponseDto(model: SecurityProperties.UserPasswordStrength) {
    var minLowerCase: Int = model.passwordMinLowerCase /*Минимальное количество прописных символов*/
    var minNumber: Int = model.passwordMinNumber /*Минимальное количество цифр*/
    var minSymbol: Int = model.passwordMinSymbol /*Минимальное количество спец символов*/
    var minUpperCase: Int = model.passwordMinUpperCase /*Минимальное количество заглавных символов*/
    var minCharacters: Int = model.passwordMinCharacters /*Минимальная длина пароля*/
    var passwordExpired: Long = model.passwordExpired
    var unit: ChronoUnit = model.unit
}
