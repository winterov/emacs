package ru.emacs.users.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.emacs.users.services.UserPhoneService

@RestController
@RequestMapping("api/v1/users/phone")
internal class UserPhoneControllerApiV1 @Autowired constructor(
    private val userPhoneService: UserPhoneService
) {

    @GetMapping("check")
    fun emailBusyCheck(@RequestParam phone: String): ResponseEntity<Any> {
        val responseParam = userPhoneService.checkPhoneNumberForBusy(phone)
        return ResponseEntity(responseParam.first,responseParam.second)
    }
}