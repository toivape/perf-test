package com.example.legacybackend

import org.springframework.stereotype.Service
import java.time.Duration

@Service
class LegacyService(val legacyRepo: LegacyRepo) {

    fun getLegacy(id: Long): Legacy? {
        // Simulate a slow response
        Thread.sleep(Duration.ofSeconds(5).toMillis())
        return legacyRepo.findById(id).orElse(null)
    }

    fun getLegacyByName(name: String): Legacy? {
        return legacyRepo.findByName(name)
    }

    fun getAllLegacy(): List<Legacy> {
        return legacyRepo.findAll().toList()
    }

    fun saveLegacy(legacy: Legacy): Legacy {
        return legacyRepo.save(legacy)
    }

    fun deleteLegacy(id: Long) {
        legacyRepo.deleteById(id)
    }

    fun deleteAllLegacy() {
        legacyRepo.deleteAll()
    }

    fun updateLegacy(legacy: Legacy): Legacy {
        return legacyRepo.save(legacy)
    }
}
