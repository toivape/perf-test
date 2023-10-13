package com.example.legacybackend

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import mu.KotlinLogging
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger {}

@RestController
@RequestMapping("/api", produces = ["application/json"])
class DelayController {

    @Operation(
        summary = "Echo delay message. The response will be delayed by the time specified in the message.",
    )
    @ApiResponse(
        responseCode = "200",
        description = "Delay message",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = Delay::class))],
    )
    @PostMapping("/delay")
    fun delayPost(@RequestBody delay: Delay): ResponseEntity<Delay> = getDelayResponse(delay.delayMs)

    @Operation(
        summary = "Get delay message. The response will be delayed by the time specified in path variable.",
    )
    @ApiResponse(
        responseCode = "200",
        description = "Delay message",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = Delay::class))],
    )
    @GetMapping("/delay/{delayMs}")
    fun delayGet(
        @Parameter(description = "Delay time in milliseconds", required = true, example = "5000", schema = Schema(type = "long"))
        @PathVariable
        delayMs: Long,
    ): ResponseEntity<Delay> = getDelayResponse(delayMs)

    private fun getDelayResponse(delayMs: Long): ResponseEntity<Delay> {
        log.info { ">>>>> Delaying for $delayMs ms" }
        Thread.sleep(delayMs)
        log.info { "<<<<< Done delaying for $delayMs ms" }
        return ResponseEntity.ok(Delay(delayMs))
    }
}

@Schema(name = "Delay", description = "Delay time in milliseconds", example = """{"delayMs": 5000}""")
data class Delay(
    @Schema(
        description = "Delay in milliseconds",
        type = "integer",
        requiredMode = Schema.RequiredMode.REQUIRED,
    )
    val delayMs: Long,
)
