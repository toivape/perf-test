package com.example.legacybackend.person

import org.springframework.data.repository.CrudRepository

interface PersonRepo : CrudRepository<Person, Long>
