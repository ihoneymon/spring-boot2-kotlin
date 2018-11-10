package io.honeymon.boot.springboot2kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SpringBoot2KotlinApplication

fun main(args: Array<String>) {
    runApplication<SpringBoot2KotlinApplication>(*args)
}
