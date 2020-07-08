package com.example.APIServer.repositories;

import com.example.APIServer.entities.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity, Integer> {
    @Query("select log from LogEntity log where log.changedTime>=:time and log.status.statusValue=:status")
    List<LogEntity> findAllByTimeAndStatus(@Param("time") long time, @Param("status") String statusValue);
}
