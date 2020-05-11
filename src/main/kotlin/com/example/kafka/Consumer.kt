package com.example.kafka

import org.apache.kafka.common.TopicPartition
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.listener.ConsumerSeekAware
import org.springframework.kafka.listener.ConsumerSeekAware.ConsumerSeekCallback
import org.springframework.stereotype.Component


//@Component
//class Consumer: ConsumerSeekAware {
//
//    @KafkaListener(topics = ["8n903ims-default"], groupId = "8n903ims-consumers")
//    fun listen(message: String) {
//        println("Received Messasge in group foo: $message")
//    }
//
//    override fun onPartitionsAssigned(
//        assignments: MutableMap<TopicPartition, Long>,
//        callback: ConsumerSeekCallback
//    ) {
//        assignments.keys.stream()
//            .filter { partition: TopicPartition -> "8n903ims-default" == partition.topic() }
//            .forEach { partition: TopicPartition ->
//                callback.seekToBeginning(
//                    partition.topic(),
//                    partition.partition()
//                )
//            }
//    }
//}
