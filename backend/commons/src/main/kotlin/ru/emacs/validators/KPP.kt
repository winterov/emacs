package ru.emacs.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [KPPValidator::class])
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD
)
@Retention(
    AnnotationRetention.RUNTIME
)
annotation class KPP(
    val required: Boolean = true,
    val message: String = "{validation.kpp.NotValid}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
