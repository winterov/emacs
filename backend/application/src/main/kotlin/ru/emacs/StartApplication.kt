package ru.emacs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration
import org.springframework.boot.runApplication
import ru.emacs.logging.LogbackTablesInit

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class,
    UserDetailsServiceAutoConfiguration::class])
class StartApplication

fun main(args: Array<String>) {
    LogbackTablesInit()
        .createTables()
    runApplication<StartApplication>(*args)
}