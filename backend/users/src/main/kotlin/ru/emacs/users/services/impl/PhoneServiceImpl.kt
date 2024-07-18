package ru.emacs.users.services.impl

import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import ru.emacs.users.services.PhoneService

@Service
class PhoneServiceImpl : PhoneService {
    override fun checkPhoneNumberForBusy(phoneNumber: String): Pair<Any, HttpStatus> {
        TODO("Not yet implemented")
    }

    override fun changePhoneNumber(newNumber: String): Pair<Any, HttpStatus> {
        TODO("Not yet implemented")
    }

}