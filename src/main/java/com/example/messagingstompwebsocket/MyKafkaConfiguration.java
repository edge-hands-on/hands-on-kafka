package com.example.messagingstompwebsocket;

import com.example.messagingstompwebsocket.dto.StorageEntry;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.support.serializer.JsonSerde;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@EnableKafka
@EnableKafkaStreams
@RequiredArgsConstructor
public class MyKafkaConfiguration {

    private final KafkaProperties properties;

    private final ApplicationContext applicationContext;

    @Bean(name = KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration kStreamsConfigs() {
        Map<String, Object> props = properties.buildConsumerProperties();

        props.put(StreamsConfig.APPLICATION_ID_CONFIG, "dtkg3qob");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.StringSerde.class);
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, JsonSerde.class);

        return new KafkaStreamsConfiguration(props);
    }

    @Bean
    public KTable<String, Long> kStream(
            @Value("${app.topic.main-events}") String mainEventsTopic,
            StreamsBuilder streamBuilder
    ) {
        System.out.printf("Main topic: %s%n", mainEventsTopic);

        return streamBuilder.stream(mainEventsTopic,
                Consumed.with(Serdes.String(), new JsonSerde<>(StorageEntry.class)))
                .peek((key, entry) -> System.out.printf("Consumer updateStorage: %s%n", entry.toString()))
                .map((key, entry) -> new KeyValue<>(entry.getName(), entry.getQuantity().longValue()))
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
                .reduce(Long::sum, Materialized.as("storage-reduce-sum"));
    }
}
