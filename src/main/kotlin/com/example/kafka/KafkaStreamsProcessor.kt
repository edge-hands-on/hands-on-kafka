package com.example.kafka

import org.apache.kafka.streams.kstream.KStream
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output

interface KafkaStreamsProcessor {
    @Input("input")
    fun input(): KStream<*, *>?

    @Output("out")
    fun output(): KStream<*, *>?
}