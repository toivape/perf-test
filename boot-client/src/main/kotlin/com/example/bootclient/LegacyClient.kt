package com.example.bootclient

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(value = "legacyClient", url = "http://localhost:8080/api")
interface LegacyClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["/legacy/{id}"], produces = ["application/json"])
    fun getLegacyById(@PathVariable("id") id: Long): Legacy

    @RequestMapping(method = [RequestMethod.POST], value = ["/delay"], produces = ["application/json"])
    fun getDelay(delay: Delay): Delay
}

data class Delay(val delayMs: Long)
