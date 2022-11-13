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
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.4.1")
    implementation("io.github.cdimascio:dotenv-kotlin:6.3.1")
    implementation("ch.qos.logback:logback-classic:1.4.4")
    implementation("net.dv8tion:JDA:5.0.0-alpha.22") {
        exclude("opus-java")
    }

}

tasks.build {
    dependsOn("shadowJar")
}

tasks.compileKotlin {
    kotlinOptions {
        jvmTarget = "17"
    }
}

application {
    mainClass.set("me.cobble.MainKt")
}

