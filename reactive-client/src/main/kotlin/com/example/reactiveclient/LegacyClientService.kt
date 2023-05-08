package com.example.reactiveclient

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import mu.KotlinLogging
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

private val log = KotlinLogging.logger {}

@Service
class LegacyClientService(val legacyClient: LegacyClient) {

    fun getLegacy(id: Long): Mono<Legacy?> = legacyClient.getLegacyById(id)

    fun getDelay(delayMs: Long): Mono<Delay> = legacyClient.getDelay(Delay(delayMs)).onErrorResume(Exception::class.java) {
        log.error(it) { "LegacyClientService.getDelay: Error occurred while calling delay service: ${it.message}" }
        Mono.error(it)
    }

}


