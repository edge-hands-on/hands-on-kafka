package com.example.messagingstompwebsocket;

import lombok.Data;

import java.security.Principal;

/**
 * Custom Principal class which is used for anonymous user sessions in Websocket connection
 */
@Data
public class StompPrincipal implements Principal {

    private final String name;
}
