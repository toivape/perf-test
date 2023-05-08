package com.example.bootclient

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class LegacyClientService(val legacyClient: LegacyClient) {

    fun getLegacy(id: Long): Legacy? = legacyClient.getLegacyById(id)

    // TODO Retry on timeout
    @CircuitBreaker(name = "legacyBackend", fallbackMethod = "getDelayFallback")
    fun getDelay(delayMs: Long): Delay = legacyClient.getDelay(Delay(delayMs))

    fun getDelayFallback(delayMs: Long, e: Exception): Delay {
        log.error(e) { "getDelayFallback: Error occurred while calling legacy service: ${e.message}" }
        return Delay(-1)
    }
}
