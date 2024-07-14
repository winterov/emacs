package ru.emacs.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [TokenLengthValidator::class])
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD
)
@Retention(
    AnnotationRetention.RUNTIME
)
annotation class TokenLength(
    val message: String = "{validation.token}",
    val type: ValidatorType = ValidatorType.EMAIL_TOKEN,
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = []
) {
    enum class ValidatorType {
        EMAIL_TOKEN,
        PASSWORD_TOKEN
    }
}
