package ru.emacs.users.controllers




import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.emacs.users.dtos.email.ApprovedEmailRequestDto
import ru.emacs.users.services.UserEmailService

@RestController
@RequestMapping("api/v1/users/email")
internal class UserEmailControllerApiV1 @Autowired constructor(
    private val userEmailService: UserEmailService
) {
    @PutMapping("approved")
    fun approvedEmail(@RequestBody dto: ApprovedEmailRequestDto): ResponseEntity<Any> {
        val responseParam = userEmailService.approvedUserEmail(dto)
        return ResponseEntity(responseParam.first,responseParam.second)
    }

    @GetMapping("check")
    fun emailBusyCheck(@RequestParam email: String): ResponseEntity<Any> {
        val responseParam = userEmailService.emailBusyCheck(email)
        return ResponseEntity(responseParam.first,responseParam.second)
    }
}
