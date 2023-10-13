package com.example.reactiveclient

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class LegacyClientService(val legacyClient: LegacyClient) {

    fun getLegacy(id: Long): Mono<Person?> = legacyClient.getLegacyById(id)
}
