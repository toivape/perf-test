package com.example.bootclient

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(value = "legacyClient", url = "http://localhost:8080/api")
interface LegacyFeignClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["/persons/{id}"], produces = ["application/json"])
    fun getLegacyById(@PathVariable("id") id: Long): Person
}
