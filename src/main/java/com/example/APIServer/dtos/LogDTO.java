package com.example.APIServer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogDTO {
    private int id;
    private int userID;
    private long changedTime;
    private String newStatus;
}
