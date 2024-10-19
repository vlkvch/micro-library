group = "org.example"
version = "0.3.0"

extra["lombokVersion"] = "1.18.34"
extra["mapstructVersion"] = "1.6.2"
extra["postgresqlVersion"] = "42.7.4"
extra["springBootVersion"] = "3.3.4"
extra["springCloudVersion"] = "2023.0.3"
extra["springDocVersion"] = "2.6.0"

plugins {
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter:${property("springBootVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-web:${property("springBootVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-validation:${property("springBootVersion")}")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:${property("springBootVersion")}")
    implementation("org.springframework.cloud:spring-cloud-starter-netflix-eureka-client")

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:${property("springDocVersion")}")

    implementation("org.mapstruct:mapstruct:${property("mapstructVersion")}")

    runtimeOnly("org.postgresql:postgresql:${property("postgresqlVersion")}")

    compileOnly("org.projectlombok:lombok:${property("lombokVersion")}")

    annotationProcessor("org.projectlombok:lombok:${property("lombokVersion")}")
    annotationProcessor("org.mapstruct:mapstruct-processor:${property("mapstructVersion")}")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    testImplementation("com.h2database:h2")
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
