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
    public List<UserModel> getAll() {
        List<UserModel> pms = new ArrayList<>();
        List<UserEntity> ps = userRepository.findAll();
        for (UserEntity p :
                ps) {
            UserModel pm = new UserModel(p.getUserId(), p.getUsername(), p.getEmail(), p.getStatus().getStatusValue());
            pms.add(pm);
        }
        return pms;
    }

    @Override
    public UserModel findById(Integer integer) throws NotFoundException {
        UserEntity profile = userRepository.findById(integer)
                .orElseThrow(() ->
                        new NotFoundException("User with id " + integer + " not found")
                );
        return new UserModel(
                profile.getUserId(),
                profile.getUsername(),
                profile.getEmail().toLowerCase(),
                profile.getStatus().getStatusValue().toLowerCase());
    }

    @Override
    public Integer create(UserModel userModel) {
        return this.userRepository.save(this.convertFromModelToEntity(userModel))
                .getUserId();
    }


    public Map<String, Object> changedStatus(int profileId, String statusValue) {
        Map<String, Object> map = new HashMap<String, Object>();
        UserEntity user = this.userRepository.findById(profileId)
                .orElseThrow(() ->
                        new NotFoundException("User " + profileId + " is not exists")
                );
        StatusEntity oldStatus = user.getStatus();
        map.put("profileId", user.getUserId());
        map.put("old status", oldStatus);
        if (!oldStatus.getStatusValue().equals(statusValue.toLowerCase())) {
            logService.create(new LogModel(profileId, statusValue));
        }

        this.userRepository.setStatusById(statusRepository.findFirstByStatusValue(statusValue)
                        .orElseGet(() -> {
                            return statusRepository.save(new StatusEntity(statusValue.toLowerCase()));
                        })
                , profileId);
        map.put("new status", statusRepository.findFirstByStatusValue(statusValue).get());
        return map;
    }


    public UserEntity convertFromModelToEntity(UserModel profileModel) {
        StatusEntity stat;
        try {
            stat = statusRepository.findFirstByStatusValue(profileModel.getStatus().toLowerCase())
                    .orElseGet(() -> {
                        return statusRepository.save(new StatusEntity(profileModel.getStatus().toLowerCase()));
                    });
        } catch (NullPointerException e) {
            stat =statusRepository.findFirstByStatusValue(null)
                    .orElseGet(() -> {
                        return statusRepository.save(new StatusEntity(null));
                    });
        }
        return new UserEntity(
                stat,
                profileModel.getUsername().toLowerCase(),
                profileModel.getEmail().toLowerCase()
        );
    }


}
