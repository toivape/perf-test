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
    fun getLegacyByName(@PathVariable id: Long): ResponseEntity<Person> {
        return service.getLegacy(id)
            ?.let { ResponseEntity.ok(it) }
            ?: ResponseEntity.notFound().build()
    }
}
