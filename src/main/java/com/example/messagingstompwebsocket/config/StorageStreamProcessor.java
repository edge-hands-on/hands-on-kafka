package com.example.messagingstompwebsocket.config;

import com.example.messagingstompwebsocket.dto.StorageEntry;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;

public interface StorageStreamProcessor {

    String INPUT = "event";

    String OUTPUT = "event-storage";

    @Input(INPUT)
    KStream<byte[], StorageEntry> input();

    @Output(OUTPUT)
    KStream<String, Long> output();

}
