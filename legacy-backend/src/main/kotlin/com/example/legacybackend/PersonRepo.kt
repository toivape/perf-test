package com.example.legacybackend

import org.springframework.data.repository.CrudRepository

interface PersonRepo : CrudRepository<Person, Long>
