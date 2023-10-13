package com.example.legacybackend

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class PersonService(
    val personRepo: PersonRepo,
    @Value("\${person-service.delay-seconds:5}")
    private val slowResponseDelay: Long,
) {

    fun getPerson(id: Long): Person? {
        // Simulate a slow response
        Thread.sleep(Duration.ofSeconds(slowResponseDelay).toMillis())
        return personRepo.findById(id).orElse(null)
    }

    fun findAllPersons(): List<Person> {
        return personRepo.findAll().toList()
    }
}
