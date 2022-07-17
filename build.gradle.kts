plugins {
    kotlin("jvm") version "1.7.10"
    kotlin("plugin.serialization") version "1.7.10"
    id("com.github.johnrengelman.shadow") version "7.1.2"
    application
}

group = "me.cobble"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.3")
    implementation("com.squareup.okhttp3:okhttp:3.9.1")
    implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
    implementation("net.dv8tion:JDA:5.0.0-alpha.11") {
        exclude("opus-java")
    }

}

tasks.test {
    useJUnitPlatform()
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

application {
    mainClass.set("me.cobble.MainKt")
}

