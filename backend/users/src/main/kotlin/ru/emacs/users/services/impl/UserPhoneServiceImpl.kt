package ru.emacs.users.services.impl

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.emacs.dtos.AppResponseErrorDto
import ru.emacs.users.agregators.EUserStatus
import ru.emacs.users.repositories.UserPhoneRepository
import ru.emacs.users.services.UserPhoneService
import ru.emacs.validators.PhoneNumberValidator

@Service
internal class UserPhoneServiceImpl @Autowired constructor(
    private val userPhoneRepository: UserPhoneRepository,
    private val messageSource: MessageSource
): UserPhoneService {
    private val statuses = listOf(EUserStatus.NEW_USER, EUserStatus.ACTIVE)
    override fun checkPhoneNumberForBusy(phoneNumber: String): Pair<Any?, HttpStatus> {
        val phoneNumberValidator = PhoneNumberValidator()
        if(!phoneNumberValidator.isValid(phoneNumber,null)){
            val notValidMessage:String =  messageSource.getMessage(
                "validation.phone.NotValid",null,
                LocaleContextHolder.getLocale())
            val errorDto = AppResponseErrorDto(HttpStatus.BAD_REQUEST,notValidMessage )
            return Pair(errorDto,HttpStatus.BAD_REQUEST)
        }
        if(userPhoneRepository.countOfUsageEmail(phoneNumber,statuses) > 0){
            val notValidMessage:String =  messageSource.getMessage(
                "phone.phoneIsBusy",null,
                LocaleContextHolder.getLocale())
            val errorDto = AppResponseErrorDto(HttpStatus.CONFLICT, notValidMessage)
            return Pair(errorDto, HttpStatus.CONFLICT)
        }
        return Pair(null,HttpStatus.OK)
    }

    override fun changePhoneNumber(newNumber: String): Pair<Any, HttpStatus> {
        TODO("Not yet implemented")
    }

}