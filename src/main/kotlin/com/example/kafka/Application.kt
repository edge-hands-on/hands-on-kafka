package com.example.kafka

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.kafka.core.KafkaTemplate

@SpringBootApplication
class Application(
    private val kafkaTemplate: KafkaTemplate<String, ByteArray>
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val dto = Dto.newBuilder()
            .setValue("Hello3")
            .build()

        kafkaTemplate.send("8n903ims-in-protobuf", dto.toByteArray())
    }
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
