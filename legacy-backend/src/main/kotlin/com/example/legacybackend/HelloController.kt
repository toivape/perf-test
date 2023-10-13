package com.example.legacybackend

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api", produces = ["application/json"])
class HelloController : HelloContract {

    override fun home(): Hello {
        return Hello("World!")
    }
}
