package com.example.reactiveclient


import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration


@Configuration
class LegacyClientConfig {

    @Bean
    fun legacyWebClient(): WebClient {

        val connProvider = ConnectionProvider
            .builder("webclient-conn-pool")
            .maxConnections(1000)
            .maxIdleTime(Duration.ofSeconds(1))
            .maxLifeTime(Duration.ofSeconds(70))
            .pendingAcquireMaxCount(3000)
            .pendingAcquireTimeout(Duration.ofMillis(40000))
            .build()


        val httpClient = HttpClient
            .create(connProvider)
            .responseTimeout(Duration.ofSeconds(50))
            //.wiretap(this.javaClass.canonicalName, LogLevel.INFO, AdvancedByteBufFormat.TEXTUAL)

        return WebClient.builder()
            .baseUrl("http://localhost:8080")
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }

}
