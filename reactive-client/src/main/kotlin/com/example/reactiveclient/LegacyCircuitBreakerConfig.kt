package com.example.reactiveclient

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

@Configuration
class LegacyCircuitBreakerConfig {

    private fun getTimeLimiterConfig(): TimeLimiterConfig {
        return TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(7)).build()
    }

    @Bean
    fun legacyBackendCustomizer(): Customizer<ReactiveResilience4JCircuitBreakerFactory> {
        return Customizer { factory ->
            factory.configure({ builder ->
                builder.timeLimiterConfig(getTimeLimiterConfig())
                    .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
            }, "legacyBackend")
        }
    }
}
