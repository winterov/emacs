package ru.emacs.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class KPPValidator: ConstraintValidator<KPP, String?> {
    override fun isValid(kpp: String?, p1: ConstraintValidatorContext?): Boolean {
        if (kpp == null) return false
        try {
            kpp.toLong()
        }catch (e:NumberFormatException){
            return false
        }
        return kpp.length==9
    }
}