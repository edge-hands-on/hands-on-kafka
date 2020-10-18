package com.example.messagingstompwebsocket.service;

import com.example.messagingstompwebsocket.dto.StorageEntry;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final InteractiveQueryService interactiveQueryService;

    private final SimpMessagingTemplate simpMessagingTemplate;

    private List<String> connectedUsers = new ArrayList<>();

    public void addUser(String user) {
        connectedUsers.add(user);
    }

    public void removeUser(String user) {
        connectedUsers.remove(user);
    }

    public List<StorageEntry> getStorageStatus() {
        ArrayList<StorageEntry> storage = new ArrayList<>();

        try {
            ReadOnlyKeyValueStore<String, Long> eventStorage =
                    interactiveQueryService.getQueryableStore("storage-reduce-sum",
                            QueryableStoreTypes.keyValueStore());

            KeyValueIterator<String, Long> storageIterator = eventStorage.all();
            try {
                KeyValue<String, Long> item = storageIterator.next();
                while (item != null) {
                    storage.add(StorageEntry.builder()
                            .name(item.key)
                            .quantity(item.value.intValue())
                            .build()
                    );
                    System.out.printf("Adding key: %s, value: %s%n", item.key, item.value);

                    item = storageIterator.next();
                }
            } catch (NoSuchElementException ex) {
                System.out.println("No more elements");
            }
        } catch (InvalidStateStoreException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return storage;
    }

    @Scheduled(fixedRateString = "5000", initialDelayString = "10000")
    public void schedulingTask() {
        System.out.println("Scheduler running");
        List<StorageEntry> storageStatus = this.getStorageStatus();
        connectedUsers.forEach(user -> {
            System.out.printf("Sending to user: %s%n", user);
            simpMessagingTemplate.convertAndSendToUser(user, "/topic/storage/status", storageStatus);
        });
    }

    /**
     * Handles WebSocket connection events
     */
    @EventListener(SessionConnectEvent.class)
    public void handleWebsocketConnectListener(SessionConnectEvent event) {
        System.out.printf("New connected user: %s%n", event.getUser().getName());
        addUser(event.getUser().getName());
    }

    /**
     * Handles WebSocket disconnection events
     */
    @EventListener(SessionDisconnectEvent.class)
    public void handleWebsocketDisconnectListener(SessionDisconnectEvent event) {
        System.out.printf("Disconnected user: %s%n", event.getUser().getName());
        removeUser(event.getUser().getName());
    }
}
