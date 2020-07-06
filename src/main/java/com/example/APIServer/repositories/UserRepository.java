package com.example.APIServer.repositories;

import org.springframework.data.repository.CrudRepository;
import com.example.APIServer.domain.User;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

public interface UserRepository extends CrudRepository<User, Integer> {
}
