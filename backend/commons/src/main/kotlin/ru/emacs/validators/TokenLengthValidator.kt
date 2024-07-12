package ru.emacs.validators


import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import ru.emacs.services.SecurityPropertiesService
import ru.emacs.validators.TokenLength.ValidatorType


class TokenLengthValidator(private val securityPropertiesService: SecurityPropertiesService) :
    ConstraintValidator<TokenLength, String?> {
    private var validatorType: ValidatorType? = null

    override fun initialize(constraintAnnotation: TokenLength) {
        super.initialize(constraintAnnotation)
        this.validatorType = constraintAnnotation.type

    }

    override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
        if (value.isNullOrEmpty()) {
            return false
        }
        if (this.validatorType == ValidatorType.EMAIL_TOKEN) {
            return value.length == securityPropertiesService.getSecurityProperty().approvedEmailProperty.approvedEmailTokenLength
        }
        if (this.validatorType == ValidatorType.PASSWORD_TOKEN) {
            return value.length == securityPropertiesService.getSecurityProperty().restorePasswordTokenProperty.restorePasswordTokenLength
        }
        return false
    }
}
