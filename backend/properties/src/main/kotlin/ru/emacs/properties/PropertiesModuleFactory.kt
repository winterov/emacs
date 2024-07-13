package ru.emacs.properties

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration

@Configuration
@ComponentScan("ru.emacs.properties")
open class PropertiesModuleFactory {

    @PostConstruct
    private fun greeting() {
        println("Добро пожаловать в CosmoZooProperties!")
    }
}