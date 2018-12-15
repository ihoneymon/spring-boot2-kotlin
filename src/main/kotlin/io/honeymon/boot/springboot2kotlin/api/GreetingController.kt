package io.honeymon.boot.springboot2kotlin.api

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class GreetingController {
    @GetMapping("/greeting")
    fun greeting(@RequestParam(defaultValue = "world", required = false) name: String?) = "Hello, $name"
}