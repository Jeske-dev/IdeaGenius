import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.3"
    id("io.spring.dependency-management") version "1.1.3"
    kotlin("jvm") version "1.8.22"
    kotlin("plugin.spring") version "1.8.22"
}

group = "de.jeske"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jdbc")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb-reactive")
    implementation("org.springframework.boot:spring-boot-starter-web")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    // https://mvnrepository.com/artifact/org.mockito.kotlin/mockito-kotlin
    testImplementation("org.mockito.kotlin:mockito-kotlin:3.2.0")

    testImplementation("io.projectreactor:reactor-test")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")

    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.5.1")
    implementation("org.mongodb:bson-kotlinx:4.10.1")

    implementation("org.mongodb:mongodb-driver-kotlin-coroutine:4.10.1")

    // OpenAI
    // https://github.com/Aallam/openai-kotlin
    implementation("com.aallam.openai:openai-client:3.4.0")
    implementation("io.ktor:ktor-client-okhttp:2.3.4")

    //runtimeOnly("io.ktor:ktor-client-java")
    //implementation("io.ktor:ktor-client-curl:3.4.0")


    //implementation("com.aallam.openai:openai-client-bom:3.4.0")
    //implementation("com.aallam.openai:openai-client")
    //runtimeOnly("io.ktor:ktor-client-okhttp")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs += "-Xjsr305=strict"
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
