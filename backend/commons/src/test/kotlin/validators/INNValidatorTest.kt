package validators

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.emacs.validators.INNValidator

class INNValidatorTest {
    private val validator = INNValidator()
    private val correctInnUl = "7811788730"
    @Test
    fun testUlINNCorrect(){
        Assertions.assertTrue(validator.isValid(correctInnUl,null))
    }
    @Test
    fun testUlINNinCorrectLength(){
        var incorrectLengthINN = "78117887301"
        Assertions.assertFalse(validator.isValid(incorrectLengthINN,null))
        incorrectLengthINN = "781178873"
        Assertions.assertFalse(validator.isValid(incorrectLengthINN,null))
    }

    @Test
    fun testUlINNinCorrectSymbols(){
        val incorrectLengthINN = "781178873A"
        Assertions.assertFalse(validator.isValid(incorrectLengthINN,null))
    }

    @Test
    fun testUlINNinCorrectCRC(){
        val incorrectLengthINN = "781178873"
        Assertions.assertFalse(validator.isValid(incorrectLengthINN,null))
    }
}