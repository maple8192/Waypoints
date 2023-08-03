plugins {
    kotlin("jvm") version "1.9.0"
}

group = "io.github.maple8192"
version = "1.0.0"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    testImplementation(kotlin("test"))
    compileOnly("io.papermc.paper:paper-api:1.20.1-R0.1-SNAPSHOT")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain { languageVersion.set(JavaLanguageVersion.of(17)) }
}

tasks.withType<ProcessResources> {
    inputs.properties("version" to version)
    filteringCharset = "UTF-8"
    filesMatching("plugin.yml") { expand("version" to version) }
}