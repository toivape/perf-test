package com.example.reactiveclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveClientApplication

fun main(args: Array<String>) {
    runApplication<ReactiveClientApplication>(*args)
}
