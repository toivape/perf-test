package com.example.reactiveclient

import mu.KotlinLogging
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import reactor.core.publisher.Mono

private val log = KotlinLogging.logger {}

@Service
class LegacyClient(private val legacyWebClient: WebClient, private val reactiveCircuitBreakerFactory: ReactiveResilience4JCircuitBreakerFactory) {

    fun getLegacyById(id: Long) =
        legacyWebClient.get()
            .uri("/api/legacy/{id}", id)
            .retrieve()
            .bodyToMono(Legacy::class.java)

    fun getDelay(delay: Delay): Mono<Delay> {
        return legacyWebClient.post()
            .uri("/api/delay")
            .body(Mono.just(delay), Delay::class.java)
            .retrieve()
            .onStatus({ it.is4xxClientError }) { resp ->
                log.error { "Request $delay failed with 4XX error code: ${resp.statusCode()}" }
                Mono.error(BadRequestException("Request $delay failed with error: ${resp.statusCode()}"))
            }
            .onStatus({ it.isError }) { resp ->
                log.error { "There was http error. Request $delay failed with error code: ${resp.statusCode()}" }
                Mono.error(IntegrationException("Request $delay failed with error: ${resp.statusCode()}"))
            }
            .bodyToMono(Delay::class.java)
        // .timeout(java.time.Duration.ofSeconds(7)) // This timeout value comes from the Mono publisher class. Nothing to do with http.
            /*.transform {
                val rcb = reactiveCircuitBreakerFactory.create("customer-service")
                rcb.run(it) { t ->
                    log.error(t) { "LegacyClient Transform fallback: Error occurred while calling legacy service: ${t.message}" }
                    Mono.just(Delay(-1))
                }
            }*/
    }
}

data class Delay(val delayMs: Long)

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class IntegrationException(msg: String) : Exception(msg)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(msg: String) : Exception(msg)