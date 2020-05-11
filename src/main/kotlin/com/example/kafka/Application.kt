package com.example.kafka

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
//import org.springframework.kafka.core.KafkaTemplate

@SpringBootApplication
class Application(
//    private val kafkaTemplate: KafkaTemplate<String, String>
) : CommandLineRunner {
    override fun run(vararg args: String?) {
//        kafkaTemplate.send("8n903ims-default", "Hello world 2")
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
