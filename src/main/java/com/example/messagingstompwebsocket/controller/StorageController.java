package com.example.messagingstompwebsocket.controller;

import com.example.messagingstompwebsocket.dto.StorageEntry;
import com.example.messagingstompwebsocket.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class StorageController {

    private final KafkaTemplate<String, StorageEntry> kafkaTemplate;

    private final StorageService storageService;

    @MessageMapping("/storage")
    public void storageAdd(StorageEntry entry) {
        System.out.printf("Stomp StorageController: %s%n", entry);

        kafkaTemplate.send("dtkg3qob-events", null, entry);
    }

    @MessageMapping("/storage/status")
    @SendToUser("/topic/storage/status")
    public List<StorageEntry> getStorageStatus(Principal principal) {
        System.out.println(principal.getName());

        return storageService.getStorageStatus();
    }
}
