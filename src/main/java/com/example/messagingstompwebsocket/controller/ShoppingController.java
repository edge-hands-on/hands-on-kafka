package com.example.messagingstompwebsocket.controller;

import com.example.messagingstompwebsocket.dto.Product;
import com.example.messagingstompwebsocket.service.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ShoppingController {

    private final StorageService storageService;

    @MessageMapping("/shopping")
    public void shopping(Product product) {
        System.out.println(product);
    }

    @MessageMapping("/test")
    public void test(Product product) {
        storageService.getStorageStatus();
    }
}
