package com.example.bootclient

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/boot")
class LegacyClientController(val service: LegacyClientService) {

    @GetMapping("/data/{id}")
    fun getLegacyByName(@PathVariable id: Long): ResponseEntity<Legacy> {
        return service.getLegacy(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/delay/{delayMs}")
    fun getDelay(@PathVariable delayMs: Long): ResponseEntity<Delay> {
        val delay = service.getDelay(delayMs)
        return ResponseEntity.ok(delay)
    }

    @GetMapping("/not-found")
    fun getNotFound(): String? {
        return service.getNotFound()
    }

    @GetMapping("/bad-request")
    fun getBadRequest(): String? {
        return service.getBadRequest()
    }
}
