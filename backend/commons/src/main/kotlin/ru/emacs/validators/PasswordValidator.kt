package ru.emacs.validators



import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.emacs.models.SecurityProperties
import ru.emacs.services.SecurityPropertiesService

import java.util.regex.Pattern

@Component
class PasswordValidator @Autowired constructor(private val securityPropertiesService: SecurityPropertiesService) : ConstraintValidator<Password?, String?> {
    private lateinit var userPasswordStrength: SecurityProperties.UserPasswordStrength

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null || value.trim { it <= ' ' }.isEmpty() || value.length >= 50) {
            return false
        }
        if (userPasswordStrength.passwordMinCharacters != 0 && (value.length < userPasswordStrength.passwordMinCharacters)) {
            return false
        }

        val sb = StringBuilder()
        if (userPasswordStrength.passwordMinNumber != 0
        ) {
            sb.append("(?=([^0-9]*[0-9])")
                .append("{")
                .append(userPasswordStrength.passwordMinNumber)
                .append(",})")
        }
        if (userPasswordStrength.passwordMinSymbol != 0
        ) {
            sb.append("(?=(.*[$@$!%*?&])")
                .append("{")
                .append(userPasswordStrength.passwordMinSymbol)
                .append(",})")
        }
        if (userPasswordStrength.passwordMinLowerCase != 0
        ) {
            sb.append("(?=([^a-z]*[a-z])")
                .append("{")
                .append(userPasswordStrength.passwordMinLowerCase)
                .append(",})")
        }
        if (userPasswordStrength.passwordMinUpperCase != 0
        ) {
            sb.append("(?=([^A-Z]*[A-Z])")
                .append("{")
                .append(userPasswordStrength.passwordMinUpperCase)
                .append(",})")
        }
        if (sb.isEmpty()) {
            return true
        }
        /*(?=([^a-z]*[a-z]){1,})(?=([^A-Z]*[A-Z]){1,})(?=([^0-9]*[0-9]){1,})*/
        val pattern = Pattern.compile(sb.toString())
        val matcher = pattern.matcher(value)
        return (matcher.find())
    }

    override fun initialize(constraintAnnotation: Password?) {
        super.initialize(constraintAnnotation)
        this.userPasswordStrength = securityPropertiesService.getSecurityProperty().userPasswordStrength
    }
}
