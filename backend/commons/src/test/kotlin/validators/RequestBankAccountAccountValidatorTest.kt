package validators

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.emacs.validators.BankAccountValidator

class RequestBankAccountAccountValidatorTest {
    private val validator = BankAccountValidator()
    private val correctBIC = "044525700"
    private val correctPaymentAccount = "40702810300000116486"
    private val correctCorespondentAccount = "30101810200000000700"
    @Test
    fun testBICCorrect(){
        Assertions.assertTrue(validator.validateBIC(correctBIC))
    }
    @Test
    fun testBICWrong(){
        val unCorrectBIC = "0440306531"
        Assertions.assertFalse(validator.validateBIC(unCorrectBIC))
    }

    @Test
    fun testCorrectPaymentAccount(){
        Assertions.assertTrue(validator.accountValidate(correctPaymentAccount, correctBIC))
    }
    @Test
    fun testWrongPaymentAccount(){
        val wrongPaymentAccount = "40702810300000116487"
        Assertions.assertFalse(validator.accountValidate(wrongPaymentAccount, correctBIC))
    }
    @Test
    fun testCorrectCorespondentAccount(){
        Assertions.assertTrue(validator.correspondentAccountValidate(correctCorespondentAccount, correctBIC))
    }
}