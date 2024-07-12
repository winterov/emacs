package ru.emacs.validators

import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import java.lang.Integer.parseInt

class INNValidator: ConstraintValidator<INN, String?> {
    private val innUl:Array<Int> = arrayOf(2, 4, 10, 3, 5, 9, 4, 6, 8)
    private val innFl11:Array<Int> = arrayOf(7, 2, 4, 10, 3, 5, 9, 4, 6, 8)
    private val innFl12:Array<Int> = arrayOf(3, 7, 2, 4, 10, 3, 5, 9, 4, 6, 8)
    var validatorType = INNValidatorType.INN_HUMAN

    override fun initialize(constraintAnnotation: INN) {
        super.initialize(constraintAnnotation)
        this.validatorType = constraintAnnotation.type

    }
    override fun isValid(inn: String?, p1: ConstraintValidatorContext?): Boolean {
        if (inn == null) return false
        try {
            inn.toLong()
        }catch (e:NumberFormatException){
           return false
        }
        return when(inn.length){
            10 -> validateINNBusiness(inn.toCharArray())
            12 -> validateINNHuman(inn.toCharArray())
            else-> false
        }

    }
    private fun validateINNBusiness(inn: CharArray):Boolean{

        val check = checkDigits(inn,innUl)
        return check== parseInt(inn[9].toString())
    }
    private fun validateINNHuman(inn: CharArray):Boolean{
        val check11 = checkDigits(inn,innFl11)
        val check12 = checkDigits(inn,innFl12)
        return parseInt(inn[10].toString())==check11&&parseInt(inn[11].toString())==check12
    }

    private fun checkDigits(inn:CharArray,coefficients:Array<Int>):Int{
        var n = 0
        for (i in coefficients.indices) {
            n += coefficients[i] * parseInt(inn[i].toString())
        }
        return (n % 11 % 10)
    }
}