package ru.emacs.validators

import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [OGRNValidator::class])
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD
)
@Retention(
    AnnotationRetention.RUNTIME
)
annotation class OGRN(
    val required: Boolean = true,
    val message: String = "{validation.ogrn.NotValid}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
    val type: OGRNValidatorType = OGRNValidatorType.OGRN_BUSINESS,
)
enum class OGRNValidatorType {
    OGRN_BUSINESS,
    OGRNIP_ENTREPRENEUR
}