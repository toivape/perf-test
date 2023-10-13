package com.example.legacybackend

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate

/**
 * Person entity.
 * Hibernate creates db table based on this class.
 */
@Entity
@Table(name = "person")
class Person(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(name = "name", nullable = false)
    var name: String? = null,

    @Column(name = "dob", nullable = false)
    var dateOfBirth: LocalDate? = null,

) {
    fun toPersonData(): PersonData {
        return PersonData(
            id = id!!,
            name = name!!,
            dateOfBirth = dateOfBirth!!.toString(),
        )
    }
}
