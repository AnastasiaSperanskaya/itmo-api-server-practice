package com.example.APIServer.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserModel {
    private int id;
    private String username;
    private String email;
    private String status;

    public UserModel() {}

    public UserModel(String username, String email, String status) {
        this.username = username;
        this.email = email;
        this.status = status;
    }
}
