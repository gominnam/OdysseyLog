package com.example.odysseylog

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@SpringBootApplication
@EnableJpaAuditing
class OdysseyLogApplication

fun main(args: Array<String>) {
    runApplication<OdysseyLogApplication>(*args)
}
