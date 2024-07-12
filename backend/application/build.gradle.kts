import org.jetbrains.kotlin.gradle.tasks.KotlinCompile


plugins {
    id("org.springframework.boot") version "3.2.2"
    id("io.spring.dependency-management") version "1.1.4"
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
}

dependencies {
    implementation(libs.org.springframework.boot.spring.boot.starter)
    implementation(libs.org.springframework.boot.spring.boot.starter.web)
    implementation(libs.ch.qos.logback.db.logback.classic.db)
    implementation(libs.org.postgresql.postgresql)

    implementation(project(":properties"))
    implementation(project(":users"))
}

description = "application"

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