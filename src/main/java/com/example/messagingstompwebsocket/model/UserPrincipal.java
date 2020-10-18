package com.example.messagingstompwebsocket.model;

import lombok.Data;

import java.security.Principal;

@Data
public class UserPrincipal implements Principal {

    private final String name;
}
