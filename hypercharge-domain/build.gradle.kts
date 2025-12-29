import org.springframework.boot.gradle.tasks.bundling.BootJar

description = "Hypercharge Domain Module"

plugins {
    kotlin("plugin.jpa") version "2.2.21"
}

tasks.getByName<BootJar>("bootJar") {
    enabled = false
}

tasks.getByName<Jar>("jar") {
    enabled = true
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    runtimeOnly("com.mysql:mysql-connector-j")
}
