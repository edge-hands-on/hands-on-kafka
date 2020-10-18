package com.example.messagingstompwebsocket.service;

import com.example.messagingstompwebsocket.dto.StorageEntry;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SchedulerService {

    private final StorageService storageService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final ConnectedUserStore connectedUserStore;

    @Scheduled(fixedRateString = "5000", initialDelayString = "10000")
    public void schedulingTask() {
        System.out.println("Scheduler running");
        List<StorageEntry> storageStatus = storageService.getStorageStatus();
        connectedUserStore.getConnectedUsers().forEach(user -> {
            System.out.printf("Sending to user: %s%n", user);
            simpMessagingTemplate.convertAndSendToUser(user, "/topic/storage/status", storageStatus);
        });
    }

}
