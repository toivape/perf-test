package com.example.reactiveclient

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/reactive")
class LegacyClientController(val legacyClientService: LegacyClientService) {

    @GetMapping("/data/{id}")
    fun getLegacyByName(@PathVariable id: Long): Mono<Legacy?> = legacyClientService.getLegacy(id)

    @GetMapping("/delay/{delayMs}")
    fun getDelay(@PathVariable delayMs: Long): Mono<Delay> = legacyClientService.getDelay(delayMs)
}
