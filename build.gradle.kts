buildscript {
    val kotlinVersion = "1.3.11"
    val springBootVersion = "2.1.1.RELEASE"

    repositories {
        maven("https://plugins.gradle.org/m2/")
    }

    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion")
        classpath("org.jetbrains.kotlin:kotlin-allopen:$kotlinVersion") // kotlin-spring 사용을 위해 필요하다.
        /**
         * <a href="https://kotlinlang.org/docs/reference/compiler-plugins.html#no-arg-compiler-plugin">No-arg compiler plugin</a>
         */
        classpath("org.jetbrains.kotlin:kotlin-noarg:$kotlinVersion") // kotlin-jpa 사용을 위해 필요하다.
        classpath("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
    }
}

//extra["springBootVersion"] = "2.1.1.RELEASE"

plugins {
    id("org.jetbrains.kotlin.jvm") version "1.3.11"
    id("org.asciidoctor.convert") version "1.5.9.2"
}

apply {
    plugin("kotlin")
    plugin("kotlin-spring")
    plugin("kotlin-jpa")
    plugin("idea")
    plugin("eclipse")
    plugin("org.springframework.boot")
    plugin("io.spring.dependency-management")
    plugin("org.asciidoctor.gradle.asciidoctor")
}

var asciidocSnippetsDir = file("build/generated-snippets")

tasks {
    compileKotlin {
        kotlinOptions {
            freeCompilerArgs = mutableListOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            freeCompilerArgs = mutableListOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }
    test {
        outputs.dir(asciidocSnippetsDir)
    }
    asciidoctor {
        inputs.dir(asciidocSnippetsDir)
        dependsOn(test)
    }
}


group = "io.honeymon.boot.kotlin.springboot"
version = "0.0.1-SNAPSHOT"


extra["spekVersion"] = "2.0.0-alpha.1"

repositories {
    mavenCentral()
    maven("https://dl.bintray.com/spekframework/spek-dev")
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    kapt("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.batch:spring-batch-test")
    testImplementation("org.springframework.restdocs:spring-restdocs-mockmvc")
    testImplementation("org.spekframework.spek2:spek-dsl-jvm:${extra["spekVersion"]}") {
        exclude(group = "org.jetbrains.kotlin")
    }
    testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:${extra["spekVersion"]}") {
        exclude(group = "org.junit.platform")
        exclude(group = "org.jetbrains.kotlin")
    }
}