package ru.emacs.properties.responses


import ru.emacs.properties.models.SecurityProperties



data class PasswordStrengthResponseDto(val minLowerCase: Int,
                                       val minNumber: Int,
                                       val minSymbol: Int,
                                       val minUpperCase: Int,
                                       val minCharacters: Int
    ) {
    constructor(model: SecurityProperties.UserPasswordStrength)
            : this(model.passwordMinLowerCase,
                model.passwordMinNumber,
                model.passwordMinSymbol,
                model.passwordMinUpperCase,
                model.passwordMinCharacters)
}

/*
    var minLowerCase: Int = model.passwordMinLowerCase */
/*Минимальное количество прописных символов*//*

    var minNumber: Int = model.passwordMinNumber */
/*Минимальное количество цифр*//*

    var minSymbol: Int = model.passwordMinSymbol */
/*Минимальное количество спец символов*//*

    var minUpperCase: Int = model.passwordMinUpperCase */
/*Минимальное количество заглавных символов*//*

    var minCharacters: Int = model.passwordMinCharacters */
/*Минимальная длина пароля*//*
*/