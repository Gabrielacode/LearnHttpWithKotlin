plugins {
    kotlin("jvm") version "1.9.0"
    application
   id("org.jetbrains.kotlin.plugin.serialization") version "1.6.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    // https://mvnrepository.com/artifact/com.google.code.gson/gson
    implementation("com.google.code.gson:gson:2.9.0")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core-jvm
   implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core-jvm:1.8.0")
    val ktor_version = "2.3.12"
    implementation("io.ktor:ktor-client-core:$ktor_version")
    implementation("ch.qos.logback:logback-classic:1.5.0")
    implementation("io.ktor:ktor-client-cio:$ktor_version")
// https://mvnrepository.com/artifact/io.ktor/ktor-serialization-kotlinx-json-jvm
    implementation("io.ktor:ktor-serialization-kotlinx-json-jvm:2.3.12")
// https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-serialization-json
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.1")
    // https://mvnrepository.com/artifact/com.squareup.okhttp3/okhttp
    implementation("com.squareup.okhttp3:okhttp:4.10.0")




}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}