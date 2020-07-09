package com.example.APIServer.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LogModel {
    private int id;
    private int userId;
    private long changedTime;
    private String changedStatus;

    public LogModel() {}

    public LogModel(int userId, String changedStatus) {
        this.userId = userId;
        this.changedStatus = changedStatus;
    }

    public LogModel(int userId, long changedTime, String changedStatus) {
        this.userId = userId;
        this.changedTime = changedTime;
        this.changedStatus = changedStatus;
    }

}
