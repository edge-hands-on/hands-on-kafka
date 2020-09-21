package com.example.messagingstompwebsocket.config;

import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Output;

public interface KafkaTopics {

    String STORAGE_TOPIC = "output";

    @Output(STORAGE_TOPIC)
    KStream storageTopic();
}
