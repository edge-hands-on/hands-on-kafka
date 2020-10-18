package com.example.messagingstompwebsocket.controller;

import com.example.messagingstompwebsocket.dto.StorageEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ShoppingController {

    private final KafkaTemplate<String, StorageEntry> kafkaTemplate;

    @MessageMapping("/shopping")
    public void shopping(StorageEntry entry) {
        entry.setQuantity(entry.getQuantity() * -1);

        System.out.printf("Stomp ShoppingController: %s%n", entry);

        kafkaTemplate.send("dtkg3qob-events", null, entry);
    }
}
