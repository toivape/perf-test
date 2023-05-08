package com.example.bootclient

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/boot")
class LegacyClientController(val legacyClientService: LegacyClientService) {

    @GetMapping("/data/{id}")
    fun getLegacyByName(@PathVariable id: Long): ResponseEntity<Legacy> {
        return legacyClientService.getLegacy(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/delay/{delayMs}")
    fun getDelay(@PathVariable delayMs: Long): ResponseEntity<Delay> {
        val delay = legacyClientService.getDelay(delayMs)
        return ResponseEntity.ok(delay)
    }
}
