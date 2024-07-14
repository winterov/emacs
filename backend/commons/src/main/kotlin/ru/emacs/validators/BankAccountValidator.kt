package ru.emacs.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import java.lang.Integer.parseInt

class BankAccountValidator : ConstraintValidator<BankAccount, RequestBankAccount> {

    @Autowired
    private lateinit var messageSource: MessageSource
    private val coefficientsPaymentAccount =
        arrayOf(7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1, 3, 7, 1)

    override fun isValid(account: RequestBankAccount?, context: ConstraintValidatorContext): Boolean {
        if (account == null) return false

        if (!validateBIC(account.bic())) {
            return false
        }
        val messages = ArrayList<String>()
        if (!accountValidate(account.paymentAccount(), account.bic()!!)) {
            messages.add(
                messageSource.getMessage(
                    "{validation.paymentAccount.NotValid}",
                    null,
                    LocaleContextHolder.getLocale()
                )
            )
        }
        if (!correspondentAccountValidate(account.correspondentAccount(), account.bic()!!)) {
            messages.add(
                messageSource.getMessage(
                    "{validation.correspondentAccount.NotValid}",
                    null,
                    LocaleContextHolder.getLocale()
                )
            )
        }
        if (messages.isEmpty()) return true
        context.disableDefaultConstraintViolation()
        val errorMessage = StringBuilder()
        messages.forEach { errorMessage.append(it).append("/n") }
        context.buildConstraintViolationWithTemplate(errorMessage.toString()).addConstraintViolation()
        return false
    }

     fun accountValidate(paymentAccount: String?, bic: String): Boolean {
        if (paymentAccount == null) return false
        val rawString = bic.substring(6) + paymentAccount
        val checksum: Int
        try {
            checksum = checkDigits(rawString.toCharArray(), coefficientsPaymentAccount)
        } catch (e: Exception) {
            return false
        }
        return checksum % 10 == 0
    }

    fun correspondentAccountValidate(paymentAccount: String?, bic: String): Boolean {
        if (paymentAccount == null) return false
        val rawString = "0" + bic.substring(4, 6) + paymentAccount
        println(rawString)
        val checksum: Int
        try {
            checksum = checkDigits(rawString.toCharArray(), coefficientsPaymentAccount)
        } catch (e: Exception) {
            return false
        }
        return checksum % 10 == 0

    }

    fun validateBIC(bic: String?): Boolean {
        if (bic == null) return false
        try {
            bic.toLong()
        } catch (e: NumberFormatException) {
            return false
        }
        return bic.length == 9
    }

    private fun checkDigits(account: CharArray, coefficients: Array<Int>): Int {
        var n = 0
        for (i in coefficients.indices) {
            n += coefficients[i] * parseInt(account[i].toString())
        }
        return n
    }
}