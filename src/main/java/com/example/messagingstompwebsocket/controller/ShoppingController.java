package com.example.messagingstompwebsocket.controller;

import com.example.messagingstompwebsocket.dto.Product;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class ShoppingController {

    @MessageMapping("/shopping")
//	@SendTo("/topic/greetings")
    public void shopping(Product product) throws Exception {
        System.out.println(product);
    }

}
