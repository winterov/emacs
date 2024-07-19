package validators

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import ru.emacs.validators.OGRNValidator
import ru.emacs.validators.OGRNValidatorType

class OGRNValidatorTest {
    private val validator = OGRNValidator()
    private val correctOGRN = "1035005516105"
    private val correctORNIP = "322784700285342"
    @Test
    fun testUlOGRNCorrect(){
        validator.validatorType= OGRNValidatorType.OGRN_BUSINESS
        Assertions.assertTrue(validator.isValid(correctOGRN,null))
    }
    @Test
    fun testUlOGRNinCorrectLength(){
        validator.validatorType=OGRNValidatorType.OGRN_BUSINESS
        var incorrectLengthOGRN = "78117887301"
        Assertions.assertFalse(validator.isValid(incorrectLengthOGRN,null))
        incorrectLengthOGRN = "781178873"
        Assertions.assertFalse(validator.isValid(incorrectLengthOGRN,null))
    }

    @Test
    fun testUlOGRNinCorrectSymbols(){
        validator.validatorType=OGRNValidatorType.OGRN_BUSINESS
        val incorrectLengthOGRN = "12378000A0958"
        Assertions.assertFalse(validator.isValid(incorrectLengthOGRN,null))
    }

    @Test
    fun testUlOGRNinCorrectCRC(){
        validator.validatorType=OGRNValidatorType.OGRN_BUSINESS
        val incorrectOGRNcrc = "1237800070954"
        Assertions.assertFalse(validator.isValid(incorrectOGRNcrc,null))
    }

    @Test
    fun testUlOGRNIPCorrect(){
        validator.validatorType=OGRNValidatorType.OGRNIP_ENTREPRENEUR
        Assertions.assertTrue(validator.isValid(correctORNIP,null))
    }

    @Test
    fun testUlOGRNIPinCorrectLength(){
        validator.validatorType=OGRNValidatorType.OGRNIP_ENTREPRENEUR
        var incorrectLengthOGRNIP = "78117887301000000"
        Assertions.assertFalse(validator.isValid(incorrectLengthOGRNIP,null))
        incorrectLengthOGRNIP = "781178873"
        Assertions.assertFalse(validator.isValid(incorrectLengthOGRNIP,null))
    }
    @Test
    fun testUlOGRNIPcrcWrong(){
        validator.validatorType=OGRNValidatorType.OGRNIP_ENTREPRENEUR
        val incorrectLengthOGRNIP = "322784700285341"
        Assertions.assertFalse(validator.isValid(incorrectLengthOGRNIP,null))
    }
}