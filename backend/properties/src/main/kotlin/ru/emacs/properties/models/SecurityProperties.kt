package ru.emacs.properties.models


import java.time.temporal.ChronoUnit

class SecurityProperties {
    lateinit var userPasswordStrength: UserPasswordStrength
    lateinit var jwtProperties: JwtProperties
    lateinit var restorePasswordTokenProperty: RestorePasswordTokenProperty
    lateinit var approvedEmailProperty: ApprovedEmailProperty
    lateinit var passwordProperties: PasswordProperties

    class JwtProperties {
        var secret: String = "h4f8093h4f983yhrt9834hr0934hf0hf493g493gf438rh438th34g34g"
        var jwtLifetime: Long = 60000
        var jwtRefreshLifetime: Int? = 60*60*60*1000
        var emailVerifyTokenLifeTime: Int? = null
    }

    class PasswordProperties{
        var secret:String =  "FZK2DZ82odqS13e8aENggaMbb_fAkl-nJL4AEVBX43g"
        var iteration:Int =  64
        var keyLength: Int = 256
    }
    /*Если значение установлено в 0, то при валидации не используется*/

    class UserPasswordStrength {
        var passwordExpired = 0L
        var unit = ChronoUnit.MONTHS
        var passwordMinLowerCase = 0 /*Минимальное количество прописных символов*/
        var passwordMinNumber = 0 /*Минимальное количество цифр*/
        var passwordMinSymbol = 0 /*Минимальное количество спец символов*/
        var passwordMinUpperCase = 0 /*Минимальное количество заглавных символов*/
        var passwordMinCharacters = 0 /*Минимальная длина пароля*/
    }


    class RestorePasswordTokenProperty {
        var restorePasswordTokenLength = 40
        var pauseBetweenNextTokenGenerate = 5L // в секундах минимально 1 минута
        var restorePasswordTokenLifetime = 24 * 60L
        var unit = ChronoUnit.MINUTES

        fun setPasswordTokenLength(restorePasswordTokenLength: Int) {
            this.restorePasswordTokenLength = if (restorePasswordTokenLength > 40) {
                40
            } else {
                restorePasswordTokenLength
            }
        }
    }


    class ApprovedEmailProperty {
        var approvedEmailTokenLength = 40
        val pauseBetweenNextTokenGenerate = 1L //минимально 1 минута
        val approvedEmailTokenLifetime = 24 * 60L
        val unit = ChronoUnit.MINUTES

        fun setEmailTokenLength(restorePasswordTokenLength: Int) {
            this.approvedEmailTokenLength = if (restorePasswordTokenLength > 40) {
                40
            } else {
                restorePasswordTokenLength
            }
        }

    }
}