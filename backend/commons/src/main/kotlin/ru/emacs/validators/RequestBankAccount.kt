package ru.emacs.validators


import jakarta.validation.Constraint
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Constraint(validatedBy = [BankAccountValidator::class])
@Target(
    AnnotationTarget.TYPE, AnnotationTarget.CLASS
)
@Retention(
    AnnotationRetention.RUNTIME
)
annotation class BankAccount(
    val required: Boolean = true,
    val message: String = "{validation.bic.NotValid}",
    val groups: Array<KClass<*>> = [],
    val payload: Array<KClass<out Payload>> = [],
)
interface  RequestBankAccount {
    fun bic(): String?
    fun correspondentAccount(): String?
    fun paymentAccount(): String?
}