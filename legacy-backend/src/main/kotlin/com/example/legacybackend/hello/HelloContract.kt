package com.example.legacybackend.hello

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping

@Tag(name = "Hello Controller", description = "Show that I can say hello.")
interface HelloContract {

    @Operation(
        summary = "Return a hello message JSON.",
    )
    @ApiResponse(
        responseCode = "200",
        description = "Hello message JSON.",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = Hello::class))],
    )
    @GetMapping("/hello")
    fun home(): Hello
}

@Schema(name = "Hello", description = "Hello message", example = """{"hello": "World!"}""")
data class Hello(
    @Schema(
        description = "Hello text",
        type = "string",
        example = "World!",
        requiredMode = Schema.RequiredMode.REQUIRED,
    )
    val hello: String
)
