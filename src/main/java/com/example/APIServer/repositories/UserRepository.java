package com.example.APIServer.repositories;

import com.example.APIServer.entities.StatusEntity;
import com.example.APIServer.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    @Modifying
    @Transactional
    @Query("update UserEntity user set user.status=:status where user.userId=:id")
    int setStatusById(@Param("status") StatusEntity status, @Param("id") int id);

}
