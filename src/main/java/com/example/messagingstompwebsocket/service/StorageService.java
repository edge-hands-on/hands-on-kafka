package com.example.messagingstompwebsocket.service;

import com.example.messagingstompwebsocket.dto.StorageEntry;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class StorageService {

    private final InteractiveQueryService interactiveQueryService;

    public List<StorageEntry> getStorageStatus() {
        ArrayList<StorageEntry> storage = new ArrayList<>();

        ReadOnlyKeyValueStore<String, Long> eventStorage =
                interactiveQueryService.getQueryableStore("storage-reduce-sum", QueryableStoreTypes.keyValueStore());

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

        return storage;
    }
}
