package ru.emacs.properties.controllers

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.emacs.properties.responses.PasswordStrengthResponseDto
import ru.emacs.properties.services.SecurityPropertiesService


@RestController
@RequestMapping("api/v1/properties/security")
internal class PasswordPropertiesController @Autowired constructor(private val securityProperties: SecurityPropertiesService) {

    @GetMapping("/password/strange")
    fun passwordStrangeContract(): PasswordStrengthResponseDto{
      return  PasswordStrengthResponseDto(securityProperties.getSecurityProperty().userPasswordStrength)
    }

}
