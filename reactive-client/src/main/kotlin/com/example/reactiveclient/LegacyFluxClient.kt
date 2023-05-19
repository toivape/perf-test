package com.example.reactiveclient

import mu.KotlinLogging
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
import reactor.util.retry.Retry
import java.time.Duration

private val log = KotlinLogging.logger {}

@Service
class LegacyClient(private val legacyWebClient: WebClient) {

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
            .onStatus({ it.isSameCodeAs(HttpStatus.NOT_FOUND) }) {
                log.error { "Request $delay failed with 404 error code" }
                Mono.error(NotFoundException("Request $delay not found: ${it.statusCode()}"))
            }
            .onStatus({ it.is4xxClientError }) { resp ->
                log.error { "Request $delay failed with 4XX error code: ${resp.statusCode()}" }
                Mono.error(BadRequestException("Request $delay failed with error: ${resp.statusCode()}"))
            }
            .onStatus({ it.is5xxServerError }) { resp ->
                log.error { "Request $delay failed with 5XX error code: ${resp.statusCode()}" }
                Mono.error(RetryException("Request $delay failed with service error: ${resp.statusCode()}"))
            }
            .onStatus({ it.isError }) { resp ->
                log.error { "There was http error. Request $delay failed with error code: ${resp.statusCode()}" }
                Mono.error(IntegrationException("Request $delay failed with error: ${resp.statusCode()}"))
            }
            .bodyToMono(Delay::class.java)
            .retryWhen(Retry.backoff(3, Duration.ofSeconds(5)).filter(RetryException::class.java::isInstance))

        // .timeout(java.time.Duration.ofSeconds(7)) // This timeout value comes from the Mono publisher class. Nothing to do with http.
    }
}

data class Delay(val delayMs: Long)

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
class IntegrationException(msg: String) : Exception(msg)

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(msg: String) : Exception(msg)

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(msg: String) : Exception(msg)

class RetryException(msg: String) : Exception(msg)
