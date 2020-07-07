package com.example.APIServer.repositories;
import org.springframework.data.repository.CrudRepository;
import com.example.APIServer.entities.UserEntity;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<UserEntity, Integer> {
}
