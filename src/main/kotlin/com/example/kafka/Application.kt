package com.example.kafka

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.config.TopicConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory


@SpringBootApplication
class Application(
    private val kafkaTemplate: KafkaTemplate<String, String>
) : CommandLineRunner {
    override fun run(vararg args: String?) {
        kafkaTemplate.send("test-3", "Hello world from Springboot 02/03 2")
    }
}

@Configuration
class KafkaTopicConfig {
    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        return KafkaAdmin(mapOf(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092"))
    }

    @Bean
    fun topic(): NewTopic {

        return NewTopic("test-3", 1, 1).apply {
            configs(
                mapOf(
                    TopicConfig.RETENTION_MS_CONFIG to "777600000"
                )
            )
        }
    }
}

@Configuration
class KafkaProducerConfig {
    @Bean
    fun producerFactory(): ProducerFactory<String, String> {
        return DefaultKafkaProducerFactory(
            mapOf(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to "localhost:9092",
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG to StringSerializer::class.java
            )
        )
    }

    @Bean
    fun kafkaTemplate(factory: ProducerFactory<String, String>): KafkaTemplate<String, String> = KafkaTemplate(factory)
}

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}
