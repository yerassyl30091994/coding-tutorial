import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.gradle.api.tasks.testing.logging.TestLogEvent.FAILED
import org.gradle.api.tasks.testing.logging.TestLogEvent.PASSED
import org.gradle.api.tasks.testing.logging.TestLogEvent.SKIPPED
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_ERROR
import org.gradle.api.tasks.testing.logging.TestLogEvent.STANDARD_OUT
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jmailen.gradle.kotlinter.support.ReporterType

group = "kz.btsdigtal.testingschool"

repositories {
    mavenCentral()
    jcenter()
}

plugins {
    kotlin("jvm") version "1.3.0"
    id("org.jmailen.kotlinter") version "1.18.0"
}

group = "kz.btsd"

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = "1.8"
            freeCompilerArgs = listOf("-Xjsr305=strict")
        }
    }

    withType<Test> {
        useJUnitPlatform()

        systemProperty("kotlintest.tags.include", System.getProperty("kotlintest.tags.include"))
        systemProperty("kotlintest.tags.exclude", System.getProperty("kotlintest.tags.exclude"))

        testLogging {
            showStandardStreams = true
            showStackTraces = true
            exceptionFormat = FULL
            events = mutableSetOf(PASSED, FAILED, SKIPPED, STANDARD_OUT, STANDARD_ERROR)
        }

        dependsOn("cleanTest")
    }
}

repositories {
    mavenCentral()
}

dependencies {
    val kotlintestVersion = "3.1.10"
    val javafakerVersion = "0.16"
    val slf4jSimpleVersion = "1.7.25"
    val fuelVersion = "1.16.0"

    compile(kotlin("stdlib-jdk8"))
    testCompile("io.kotlintest:kotlintest-runner-junit5:$kotlintestVersion")
    testCompile("com.github.javafaker:javafaker:$javafakerVersion")
    testCompile("org.slf4j:slf4j-simple:$slf4jSimpleVersion")
    testCompile("com.github.kittinunf.fuel:fuel:$fuelVersion")
    testCompile("com.github.kittinunf.fuel:fuel-moshi:$fuelVersion")
}

kotlinter {
    ignoreFailures = false
    indentSize = 4
    continuationIndentSize = 4
    reporters = arrayOf(ReporterType.checkstyle.name, ReporterType.plain.name, ReporterType.html.name)
}
