package com.example.messagingstompwebsocket.controller;

import com.example.messagingstompwebsocket.dto.StorageEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class StorageController {

    private final KafkaTemplate<String, StorageEntry> kafkaTemplate;

    @MessageMapping("/storage")
    public void storageAdd(StorageEntry entry) {
        System.out.printf("Stomp controller: %s%n", entry);

        kafkaTemplate.send("dtkg3qob-events", null, entry);
    }
}
