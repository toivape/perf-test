package com.example.reactiveclient

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig
import io.github.resilience4j.timelimiter.TimeLimiterConfig
import mu.KotlinLogging
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory
import org.springframework.cloud.client.circuitbreaker.Customizer
import org.springframework.cloud.client.circuitbreaker.ReactiveCircuitBreaker
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Duration

private val log = KotlinLogging.logger {}


@Configuration
class LegacyCircuitBreakerConfig {

    companion object {
        const val LEGACY_BACKEND = "legacyBackend"
    }

    private fun getTimeLimiterConfig(): TimeLimiterConfig {
        return TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(7)).build()
    }

    private fun circuitBreakerConfig(): CircuitBreakerConfig {
        return CircuitBreakerConfig.custom()
            .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
            .slidingWindowSize(2)
            .failureRateThreshold(50f)
            .waitDurationInOpenState(Duration.ofSeconds(5))
            .permittedNumberOfCallsInHalfOpenState(2)
            .slowCallDurationThreshold(Duration.ofSeconds(30))
            .build()
    }

    @Bean
    fun legacyBackendCustomizer(): Customizer<ReactiveResilience4JCircuitBreakerFactory> {
        return Customizer { factory ->
            factory.configure({ builder ->
                builder
                    // .timeLimiterConfig(getTimeLimiterConfig())
                    .circuitBreakerConfig(circuitBreakerConfig())
            }, LEGACY_BACKEND)
        }
    }

    @Bean
    fun legacyCircuitBreaker(factory: ReactiveResilience4JCircuitBreakerFactory): ReactiveCircuitBreaker {
        factory.configure({ builder -> builder.circuitBreakerConfig(circuitBreakerConfig()) }, LEGACY_BACKEND)
        val cb = factory.create(LEGACY_BACKEND)
        log.info("<<<<<<<<<<<<< Created reactive circuit breaker: $cb")
        return cb
    }
}
