package com.example.legacybackend

import mu.KotlinLogging
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
    fun delay(@RequestBody delay: Delay): ResponseEntity<Delay> {
        if (delay.delayMs == 666L) {
            log.info { "Delay is set to ${delay.delayMs}ms. Returning bad request." }
            return ResponseEntity.badRequest().build()
        }
        log.info { ">>>>> Delaying for ${delay.delayMs} ms" }
        Thread.sleep(delay.delayMs)
        log.info { "<<<<< Done delaying for ${delay.delayMs} ms" }
        return ResponseEntity.ok(delay)
    }
}

data class Delay(val delayMs: Long)
