package com.example.reactiveclient

import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class LegacyClient(private val legacyWebClient: WebClient) {

    fun getLegacyById(id: Long) =
        legacyWebClient.get()
            .uri("/api/persons/{id}", id)
            .retrieve()
            .bodyToMono(Person::class.java)
}
