package com.example.APIServer.models;

import lombok.Data;
import lombok.NonNull;

@Data
public class UserModel {

    private Long id;

    @NonNull
    private final String username;

    private final String email;

    public UserModel(@NonNull String username, String email) {
        this.username = username;
        this.email = email;
    }
}
