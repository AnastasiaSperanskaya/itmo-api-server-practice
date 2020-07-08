package com.example.APIServer.services;

import com.example.APIServer.NotFoundException;
import com.example.APIServer.entities.StatusEntity;
import com.example.APIServer.entities.UserEntity;
import com.example.APIServer.models.UserModel;
import com.example.APIServer.repositories.StatusRepository;
import com.example.APIServer.repositories.UserRepository;
import com.example.APIServer.models.LogModel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class UserService implements ITemplateService<UserModel, Integer> {
    private final UserRepository userRepository;
    private final StatusRepository statusRepository;
    private final LogService logService;


    @Override
    public List<UserModel> getAll()
    {
        List<UserModel> userModels = new ArrayList<>();
        List<UserEntity> users = userRepository.findAll();

        for (UserEntity user : users)
        {
            UserModel userModel = new UserModel(user.getUserId(), user.getUsername(), user.getEmail(), user.getStatus().getStatusValue());
            userModels.add(userModel);
        }
        return userModels;
    }

    @Override
    public UserModel findById(Integer integer) throws NotFoundException
    {
        UserEntity profile = userRepository.findById(integer).orElseThrow(() -> new NotFoundException("User with id " + integer + " not found"));

        return new UserModel(
                profile.getUserId(),
                profile.getUsername(),
                profile.getEmail().toLowerCase(),
                profile.getStatus().getStatusValue().toLowerCase());
    }

    @Override
    public Integer create(UserModel userModel)
    {
        return this.userRepository.save(this.modelToEntity(userModel)).getUserId();
    }


    public Map<String, Object> changedStatus(int userId, String statusValue)
    {

        Map<String, Object> map = new HashMap<String, Object>();
        UserEntity user = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User " + userId + " is not exists"));
        StatusEntity oldStatus = user.getStatus();
        map.put("user ID", user.getUserId());
        map.put("old status", oldStatus);

        if (!oldStatus.getStatusValue().equals(statusValue.toLowerCase()))
            logService.create(new LogModel(userId, statusValue));

        this.userRepository.setStatusById(statusRepository.findByStatusValue(statusValue).orElseGet(() -> statusRepository.save(new StatusEntity(statusValue.toLowerCase()))), userId);

        map.put("new status", statusRepository.findByStatusValue(statusValue).get());
        return map;
    }


    public UserEntity modelToEntity(UserModel userModel) {
        StatusEntity status;
        try {
            status = statusRepository.findByStatusValue(userModel.getStatus().toLowerCase()).orElseGet(() -> {
                        return statusRepository.save(new StatusEntity(userModel.getStatus().toLowerCase())); });
        } catch (NullPointerException e) {
            status =statusRepository.findByStatusValue(null).orElseGet(() -> {
                        return statusRepository.save(new StatusEntity(null)); });
        }

        return new UserEntity(
                status,
                userModel.getUsername().toLowerCase(),
                userModel.getEmail().toLowerCase());
    }


}
