group = "org.example"
version = "0.1.0"

val springBootVersion by extra("3.3.4")
val lombokVersion by extra("1.18.34")
val mapstructVersion by extra("1.6.2")
val springDocVersion by extra("2.6.0")
val postgresqlVersion by extra("42.7.4")

plugins {
    id("org.springframework.boot") version "3.3.4"
    id("java")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-web:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-validation:$springBootVersion")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:$springBootVersion")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:$springDocVersion")

    implementation("org.mapstruct:mapstruct:$mapstructVersion")

    runtimeOnly("org.postgresql:postgresql:$postgresqlVersion")

    compileOnly("org.projectlombok:lombok:$lombokVersion")

    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.mapstruct:mapstruct-processor:$mapstructVersion")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}
