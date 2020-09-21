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

//    @Qualifier(Source.OUTPUT)
//    private final MessageChannel messageChannel;

    @MessageMapping("/storage")
    public void storageAdd(StorageEntry entry) {
        System.out.println(String.format("Stomp controller: %s", entry));

//        messageChannel.send(MessageBuilder
//                .withPayload(entry)
//                .setHeader(KafkaHeaders.MESSAGE_KEY, entry.getName())
//                .build()
//        );
        kafkaTemplate.send("dtkg3qob-storage", entry.getName(), entry);
    }
}
