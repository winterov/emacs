package ru.emacs.users.services

import org.springframework.http.HttpStatus


interface UserPhoneService {
    fun checkPhoneNumberForBusy(phoneNumber: String): Pair<Any?,HttpStatus>
    fun changePhoneNumber(newNumber: String): Pair<Any,HttpStatus>
}