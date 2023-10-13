package com.example.legacybackend.person

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable

/**
 * Person contract API and Swagger definition.
 */
@Tag(name = "Person Controller", description = "Person data related endpoints.")
interface PersonContract {
    @GetMapping("/persons/{id}")
    @Operation(
        summary = "Get person by id. Result is delayed by 5 seconds to simulate a slow response.",
    )
    @ApiResponse(
        responseCode = "200",
        description = "Entity found by id.",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = PersonData::class))],
    )
    @ApiResponse(
        responseCode = "404",
        description = "Entity not found by id.",
        content = [Content()],
    )
    fun getPersonById(
        @Parameter(description = "Entity id", required = true)
        @PathVariable
        id: Long,
    ): ResponseEntity<PersonData>

    @GetMapping("/persons")
    @Operation(
        summary = "Get all persons.",
    )
    @ApiResponse(
        responseCode = "200",
        description = "List of all persons.",
        content = [Content(mediaType = "application/json", schema = Schema(implementation = PersonData::class))],
    )
    fun getAllPersons(): ResponseEntity<List<PersonData>>
}

@Schema(name = "Person", description = "Person details", example = """{"id": 1, "name": "John Doe", "dateOfBirth": "2021-08-01"}""")
data class PersonData(
    @Schema(
        description = "Entity id",
        type = "integer",
    )
    val id: Long,

    @Schema(
        description = "Person's name",
        type = "string",
        example = "John Doe",
    )
    val name: String,

    @Schema(
        description = "Person's date of birth in ISO 8601 format",
        type = "string",
        pattern = "yyyy-MM-dd",
        example = "2021-08-01",
    )
    val dateOfBirth: String,
)
