plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

group = "ru.emacs"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly(libs.org.springframework.boot.spring.boot.starter.data.jdbc)
    compileOnly(libs.org.springframework.boot.spring.boot.starter.validation)
    compileOnly(libs.org.springframework.boot.spring.boot.starter.mail)
    compileOnly(libs.kotlin.stdlib)
    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.jackson.module.kotlin)
    compileOnly(libs.apache.tika)
    compileOnly(project(":commons"))
    testImplementation(kotlin("test"))

}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}