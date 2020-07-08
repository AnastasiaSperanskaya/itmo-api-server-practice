package com.example.APIServer.repositories;

import com.example.APIServer.entities.StatusEntity;
import com.example.APIServer.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<StatusEntity, Integer> {
    StatusEntity getByStatus(Status status);
}
