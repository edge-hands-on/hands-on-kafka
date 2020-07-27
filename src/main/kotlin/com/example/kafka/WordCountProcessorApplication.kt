package com.example.kafka

import org.apache.kafka.streams.KeyValue
import org.apache.kafka.streams.kstream.KStream
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.kstream.TimeWindows
import org.apache.kafka.streams.kstream.Windowed
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.StreamListener
import org.springframework.cloud.stream.binder.kafka.streams.annotations.KafkaStreamsProcessor
import org.springframework.messaging.handler.annotation.SendTo
import java.time.Duration

@EnableBinding(KafkaStreamsProcessor::class)
class WordCountProcessorApplication {
    @StreamListener("input")
    @SendTo("output")
    fun process(input: KStream<Any?, ByteArray>): KStream<*, WordCount?> {

//        org.apache.kafka.common.serialization.Serdes.
//        org.apache.kafka.
//        org.springframework.kafka.support.serializer.JsonSerde

        return input
                .flatMapValues { bytes: ByteArray -> listOf(*Dto.parseFrom(bytes).value.toLowerCase()
                    .split("\\W+".toRegex())
                    .toTypedArray())
                }
                .map { key: Any?, value: String -> KeyValue(value, value.toByteArray()) }
                .groupByKey()
                .windowedBy(TimeWindows.of(Duration.ofMillis(5000)))
                .count(Materialized.`as`("WordCounts-state-store"))
                .toStream()
                .map<Any, WordCount> { key: Windowed<String>, value: Long ->
                    KeyValue(null, WordCount(key.key(), value))
                }
    }
}

data class WordCount(val key: String, val value: Long)
