package ru.emacs.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext

class PhoneNumberValidator : ConstraintValidator<PhoneNumber, String?> {
    private var required = true
    override fun initialize(phoneNumber: PhoneNumber) {
        super.initialize(phoneNumber)
        this.required = phoneNumber.required
    }

    override fun isValid(contactField: String?, validatorContext: ConstraintValidatorContext?): Boolean {
        if (!required && contactField.isNullOrEmpty()) {
            return true
        }
        if (contactField!=null) {
            if(contactField.length == 12){
                if(contactField.matches("^\\+\\d{11}".toRegex())){
                    return true
                }
            }
        }
        return false
       /* return contactField != null && contactField.length == 12 && contactField.matches("^\\+\\d{11}".toRegex())*/
    }
}
