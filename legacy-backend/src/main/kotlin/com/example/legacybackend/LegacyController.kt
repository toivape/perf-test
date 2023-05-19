package com.example.legacybackend

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@RestController
@RequestMapping("/api")
class LegacyController(val legacyService: LegacyService) {

    @GetMapping("/legacy/{id}")
    fun getLegacyByName(@PathVariable id: Long): ResponseEntity<Legacy> {
        return legacyService.getLegacy(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @PostMapping("/delay")
    fun delay(@RequestBody delay: Delay) =
        when (delay.delayMs) {
            400L -> ResponseEntity.badRequest().build()
            404L -> ResponseEntity.notFound().build()
            500L -> ResponseEntity.internalServerError().build()
            502L -> ResponseEntity.status(HttpStatus.BAD_GATEWAY).build()
            503L -> ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build()
            else -> {
                log.info { ">>>>> Delaying for ${delay.delayMs} ms" }
                Thread.sleep(delay.delayMs)
                log.info { "<<<<< Done delaying for ${delay.delayMs} ms" }
                ResponseEntity.ok(delay)
            }
        }

    @GetMapping("/legacy/not-found")
    fun getNotFound(): ResponseEntity<String> {
        return ResponseEntity.notFound().build()
    }

    @GetMapping("/legacy/bad-request")
    fun getBadRequest(): ResponseEntity<String> {
        return ResponseEntity.badRequest().build()
    }
}

data class Delay(val delayMs: Long)
