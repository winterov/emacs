package ru.emacs.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class OGRNValidator: ConstraintValidator<OGRN, String?> {
    var validatorType = OGRNValidatorType.OGRN_BUSINESS

    override fun initialize(constraintAnnotation: OGRN) {
        super.initialize(constraintAnnotation)
        this.validatorType = constraintAnnotation.type

    }
    override fun isValid(ogrn: String?, p1: ConstraintValidatorContext?): Boolean {
        if (ogrn == null) return false
        try {
            ogrn.toLong()
        }catch (e:NumberFormatException){
            return false
        }

        if(ogrn.length!=13&&ogrn.length!=15) return false
        return when(validatorType) {
            OGRNValidatorType.OGRN_BUSINESS->validateBusiness(ogrn)
            OGRNValidatorType.OGRNIP_ENTREPRENEUR->validateEntrepreneur(ogrn)
        }
    }

    private fun validateBusiness(ogrn:String):Boolean{
        val number = ogrn.substring(0,12).toLong()
       return number %11%10==ogrn.last().toString().toLong()
    }

    private fun validateEntrepreneur(ogrn:String):Boolean{
        val number = ogrn.substring(0,14).toLong()
        return number %13%10==ogrn.last().toString().toLong()
    }
}