package com.example.messagingstompwebsocket;

import com.example.messagingstompwebsocket.dto.StorageEntry;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
public class ConsumerConfig {

//    @StreamListener(Sink.INPUT)
    public void printNewStorageEntry(StorageEntry entry) {
        System.out.println(String.format("Kafka consumer: %s", entry));
    }
}

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
