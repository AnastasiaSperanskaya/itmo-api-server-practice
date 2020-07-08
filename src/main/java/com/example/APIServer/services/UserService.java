package com.example.APIServer.services;

import com.example.APIServer.entities.StatusEntity;
import com.example.APIServer.entities.UserEntity;
import com.example.APIServer.Status;
import com.example.APIServer.models.UserModel;
import com.example.APIServer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final StatusService statusService;

    @Autowired
    public UserService(UserRepository userRepository, StatusService statusService) {
        this.userRepository = userRepository;
        this.statusService = statusService;
    }

    public UserModel addUser(UserModel userModel) {
        StatusEntity offlineStatusEntity = statusService.getStatusEntityByStatus(Status.OFFLINE);

        UserEntity userEntity = new UserEntity(
                offlineStatusEntity,
                userModel.getUsername(),
                userModel.getEmail()
        );

        userEntity = userRepository.save(userEntity);
        userModel.setId(userEntity.getId());

        return userModel;
    }

    public UserModel getUser(Long id) {
        Optional<UserEntity> userEntityOptional = userRepository.findById(id);
        UserEntity userEntity = userEntityOptional.orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with id " + id + " was not found")
        );

        UserModel userModel = new UserModel(
                userEntity.getUsername(),
                userEntity.getEmail()
        );

        return userModel;
    }
}
