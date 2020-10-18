package com.example.messagingstompwebsocket.service;

import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Data
public class ConnectedUserStore {

    private List<String> connectedUsers = new ArrayList<>();

    public void addUser(String user) {
        connectedUsers.add(user);
    }

    public void removeUser(String user) {
        connectedUsers.remove(user);
    }
}
