package com.example.legacybackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LegacyBackendApplication

fun main(args: Array<String>) {
    runApplication<LegacyBackendApplication>(*args)
}
