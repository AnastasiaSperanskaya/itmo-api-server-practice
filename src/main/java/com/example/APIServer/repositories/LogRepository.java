package com.example.APIServer.repositories;

import com.example.APIServer.entities.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Integer> {
    List<LogEntity> findAllByChangedTimeAndStatus_StatusValue(long timestamp, String status);
}
