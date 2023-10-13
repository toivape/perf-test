package com.example.legacybackend.person

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api", produces = ["application/json"])
class PersonController(val personService: PersonService) : PersonContract {

    override fun getPersonById(id: Long): ResponseEntity<PersonData> {
        return personService.getPerson(id)
            ?.let { ResponseEntity.ok(it.toPersonData()) }
            ?: ResponseEntity.notFound().build()
    }

    override fun getAllPersons(): ResponseEntity<List<PersonData>> {
        return personService.findAllPersons()
            .map { it.toPersonData() }
            .let { ResponseEntity.ok(it) }
    }
}
