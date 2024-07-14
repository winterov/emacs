package ru.emacs.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [INNValidator::class])
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD
)
@Retention(
    AnnotationRetention.RUNTIME
)
annotation class INN(
    val required: Boolean = true,
    val message: String = "{validation.inn.NotValid}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val type: INNValidatorType = INNValidatorType.INN_BUSINESS,
)
enum class INNValidatorType {
    INN_BUSINESS,
    INN_HUMAN
}