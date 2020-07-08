package com.example.APIServer.repositories;
import org.springframework.data.repository.CrudRepository;
import com.example.APIServer.entities.UserEntity;
import com.example.APIServer.entities.StatusEntity;
import com.example.APIServer.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<List<UserEntity>> findByStatusEntity(StatusEntity statusEntity);
    boolean existsByUsernameOrEmail(String username, String email);
}
