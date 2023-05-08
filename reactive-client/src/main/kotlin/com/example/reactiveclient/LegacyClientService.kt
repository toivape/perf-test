package com.example.reactiveclient

import mu.KotlinLogging
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

private val log = KotlinLogging.logger {}

@Service
class LegacyClientService(val legacyClient: LegacyClient, val legacyCircuitBreaker: ReactiveCircuitBreaker) {

    fun getLegacy(id: Long): Mono<Legacy?> = legacyClient.getLegacyById(id)

    fun getDelay(delayMs: Long): Mono<Delay> {
        return legacyCircuitBreaker.run(
            legacyClient.getDelay(Delay(delayMs)).onErrorResume(Exception::class.java) {
                log.error(it) { "LegacyClientService.getDelay: Error occurred while calling delay service: ${it.message}" }
                Mono.error(it)
            },
        )
    }
}
