package com.example.springwebflux

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class SpringwebfluxApplication {
}

fun main(args: Array<String>) {
    runApplication<SpringwebfluxApplication>(*args)
}
