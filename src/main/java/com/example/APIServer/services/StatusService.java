package com.example.APIServer.services;

import com.example.APIServer.entities.StatusEntity;
import com.example.APIServer.Status;
import com.example.APIServer.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public StatusEntity getStatusEntityByStatus(Status status) {
        return statusRepository.getByStatus(status);
    }
}
