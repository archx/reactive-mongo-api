package com.example.reactive

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReactiveMongoApiApplication

fun main(args: Array<String>) {
    runApplication<ReactiveMongoApiApplication>(*args)
}
