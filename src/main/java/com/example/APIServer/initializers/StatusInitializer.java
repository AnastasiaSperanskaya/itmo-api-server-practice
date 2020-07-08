package com.example.APIServer.initializers;

import com.example.APIServer.entities.StatusEntity;
import com.example.APIServer.Status;
import com.example.APIServer.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StatusInitializer implements ApplicationRunner {

    private final StatusRepository statusRepository;

    @Autowired
    public StatusInitializer(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (statusRepository.count() == 0) {
            for (Status status: Status.values()) {
                statusRepository.save(new StatusEntity(status));
            }
        }
    }
}
