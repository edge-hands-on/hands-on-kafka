package com.example.kafka

import org.apache.kafka.common.TopicPartition
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.ConsumerSeekAware
import org.springframework.kafka.listener.ConsumerSeekAware.ConsumerSeekCallback
import org.springframework.stereotype.Component


@Component
class Consumer: ConsumerSeekAware {

    @KafkaListener(topics = ["test-3"], groupId = "foo1")
    fun listen(message: String) {
        println("Received Messasge in group foo: $message")
    }

    override fun onPartitionsAssigned(
        assignments: MutableMap<TopicPartition, Long>,
        callback: ConsumerSeekCallback
    ) {
        assignments.keys.stream()
            .filter { partition: TopicPartition -> "test-3" == partition.topic() }
            .forEach { partition: TopicPartition ->
                callback.seekToBeginning(
                    partition.topic(),
                    partition.partition()
                )
            }
    }
}
