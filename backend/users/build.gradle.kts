import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    kotlin("plugin.jpa") version "1.9.22"
}

dependencies {
    implementation(libs.io.jsonwebtoken.jjwt.api)
    implementation(libs.io.jsonwebtoken.jjwt.impl)
    implementation(libs.io.jsonwebtoken.jjwt.jackson)
    implementation(libs.org.springframework.boot.spring.boot.starter)
    compileOnly(libs.org.springframework.boot.spring.boot.starter.web)
    compileOnly(libs.org.springframework.boot.spring.boot.starter.data.jdbc)
    compileOnly(libs.org.springframework.boot.spring.boot.starter.validation)
    compileOnly(libs.org.springframework.boot.spring.boot.starter.security)
    compileOnly(libs.org.springframework.boot.spring.boot.starter)
    compileOnly(libs.jackson.module.kotlin)
    compileOnly(libs.kotlin.stdlib)
    compileOnly(libs.kotlin.reflect)
    compileOnly(libs.jakarta.validation.api)
    compileOnly(project(":commons"))
    compileOnly(project(":properties"))
}

description = "users"
repositories {
    mavenCentral()
}
kotlin {
    jvmToolchain(21)
}
tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
    }
}
