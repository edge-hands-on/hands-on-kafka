package com.example.messagingstompwebsocket;

import com.example.messagingstompwebsocket.dto.StorageEntry;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class MyConsumerConfig {

    @Bean
    public Consumer<KStream<byte[], StorageEntry>> updateStorage() {
        return input -> input
                .peek((key, entry) -> System.out.printf("Consumer updateStorage: %s%n", entry.toString()))
                .map((key, entry) -> new KeyValue<>(entry.getName(), entry.getQuantity().longValue()))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
                .reduce((currValue, aggValue) -> {
                    long sum = currValue + aggValue;
                    return sum == 0 ? null : sum;
                }, Materialized.as("storage-reduce-sum"));
    }
}
