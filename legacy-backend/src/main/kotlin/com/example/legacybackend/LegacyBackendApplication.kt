package com.example.legacybackend

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import io.swagger.v3.oas.annotations.servers.Server
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LegacyBackendApplication

fun main(args: Array<String>) {
    runApplication<LegacyBackendApplication>(*args)
}

@OpenAPIDefinition(
    info = Info(
        title = "My Legacy API",
        version = "1.0",
        description = "This API is so legacy. It's not even funny.",
        // you can also set license, termsOfService, etc.
    ),
    servers = [ Server(url = "http://localhost:8080", description = "Local server") ],
)
class OpenApiConfig
