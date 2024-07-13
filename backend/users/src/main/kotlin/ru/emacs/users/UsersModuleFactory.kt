package ru.emacs.users

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration


@Configuration
@ComponentScan("ru.emacs.users")
open class UsersModuleFactory {

    @PostConstruct
    private fun greeting() {
        println("Добро пожаловать в CosmoZooUser!")
    }
}