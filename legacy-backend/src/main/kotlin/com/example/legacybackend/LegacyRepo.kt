package com.example.legacybackend

import org.springframework.data.repository.CrudRepository

interface LegacyRepo : CrudRepository<Legacy, Long> {
    fun findByName(name: String): Legacy?
}
