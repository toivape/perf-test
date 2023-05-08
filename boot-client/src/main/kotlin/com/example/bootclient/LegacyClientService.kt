package com.example.bootclient

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker
import mu.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger {}

@Service
class LegacyClientService(val legacyFeignClient: LegacyFeignClient) {

    fun getLegacy(id: Long): Legacy? = legacyFeignClient.getLegacyById(id)

    // TODO Retry on timeout
    @CircuitBreaker(name = "legacyBackend", fallbackMethod = "getDelayFallback")
    fun getDelay(delayMs: Long): Delay = legacyFeignClient.getDelay(Delay(delayMs))

    fun getDelayFallback(delayMs: Long, e: Exception): Delay {
        if (e is feign.FeignException) {
            log.error(e) { "getDelayFallback: Got Feign exception: ${e.message}" }
            throw e
        }
        log.error(e) { "getDelayFallback: Circuit breaker open. Returning default value. Error: ${e.message}" }
        return Delay(-1)
    }

    @CircuitBreaker(name = "legacyBackend", fallbackMethod = "defaultFallback")
    fun getNotFound(): String? = legacyFeignClient.getNotFound()

    @CircuitBreaker(name = "legacyBackend", fallbackMethod = "defaultFallback")
    fun getBadRequest(): String? = legacyFeignClient.getBadRequest()

    fun defaultFallback(e: Exception): String? {
        if (e is feign.FeignException) {
            log.error(e) { "defaultFallback: Got Feign exception: ${e.message}" }
        }
        throw e
    }
}
