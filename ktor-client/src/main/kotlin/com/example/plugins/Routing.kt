package com.example.plugins

import io.ktor.client.* // ktlint-disable no-wildcard-imports
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    val client = HttpClient(CIO) {
        headers {
            append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
            append(HttpHeaders.Accept, ContentType.Application.Json.toString())
        }
        install(HttpTimeout) {
            requestTimeoutMillis = 40000 // Timeout for the request execution, 10 seconds
            connectTimeoutMillis = 40000 // Timeout for connecting to the remote host, 5 seconds
            socketTimeoutMillis = 40000 // Timeout for socket operations (read/write), 15 seconds
        }
    }

    routing {
        get("/ktor/hello") {
            call.respondText("Hello World!")
        }

        get("/ktor/data/{id}") {
            val id = call.parameters["id"]?.toLongOrNull() ?: 1L
            val response: HttpResponse = client.get("http://localhost:8080/api/legacy/$id")
            call.respond(HttpStatusCode.OK, response.bodyAsText())
        }
    }
}
