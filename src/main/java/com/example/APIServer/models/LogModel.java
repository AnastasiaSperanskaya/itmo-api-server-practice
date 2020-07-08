package com.example.APIServer.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LogModel {
    private int id;
    private int userId;
    private long changedTime;
    private String newStatus;

    public LogModel() {}

    public LogModel(int userId, String newStatus) {
        this.userId = userId;
        this.newStatus = newStatus;
    }

    public LogModel(int userId, long changedTime, String newStatus) {
        this.userId = userId;
        this.changedTime = changedTime;
        this.newStatus = newStatus;
    }

}
