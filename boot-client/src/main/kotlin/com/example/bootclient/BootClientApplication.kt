package com.example.bootclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class BootClientApplication

fun main(args: Array<String>) {
    runApplication<BootClientApplication>(*args)
}
