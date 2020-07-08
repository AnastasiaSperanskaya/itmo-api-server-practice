package com.example.APIServer.controllers;

import com.example.APIServer.dtos.LogDTO;
import com.example.APIServer.dtos.UserDTO;
import com.example.APIServer.models.LogModel;
import com.example.APIServer.models.UserModel;
import com.example.APIServer.services.LogService;
import com.example.APIServer.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class UserController {

    private final
    UserService userService;
    private final
    LogService logService;

    @GetMapping("/profile")
    public List<UserDTO> getProfiles()
    {
        List<UserModel> userModelList = userService.getAll();
        List<UserDTO> userDTOList = new ArrayList<>();

        for (UserModel userModel : userModelList)
        {
            UserDTO profileDTO = new UserDTO(userModel.getUsername(), userModel.getEmail(), userModel.getStatus());
            userDTOList.add(profileDTO);
        }
        return userDTOList;
    }

    @PostMapping("/profile")
    public int createProfile(@RequestBody UserDTO profile)
    {
        return userService.create(new UserModel(profile.getUserName(), profile.getEmail(), profile.getStatus()));
    }

    @GetMapping("/profile/{id}")
    public UserDTO getProfileById(@PathVariable(value = "id") Integer userId)
    {
        UserModel userModel = userService.findById(userId);
        return new UserDTO(userModel.getUsername(), userModel.getEmail(), userModel.getStatus());
    }

    @PutMapping("/profile/{id}")
    public Map<String, Object> changeStatusForId( @PathVariable(value = "id") Integer userId, @RequestParam(value = "status") String status)
    {
        return userService.changedStatus(userId, status);
    }

    @GetMapping("/logs")
    public List<LogDTO> getLogs()
    {
        List<LogModel> logModelList = logService.getAll();
        List<LogDTO> logDTOList = new ArrayList<>();
        for (LogModel log : logModelList)
        {
            LogDTO logDTO = new LogDTO(log.getId(), log.getUserId(), log.getChangedTime(), log.getNewStatus());
            logDTOList.add(logDTO);
        }
        return logDTOList;
    }

    @GetMapping("/logs/{status}")
    public List<LogDTO> getLogsByTimestampAndStatus( @PathVariable(value = "status") String status, @RequestParam(value = "timestamp", required = false, defaultValue = "0") long time)
    {
        List<LogModel> logModelList = logService.getAllByStatusAndTimestamp(time,status);
        List<LogDTO> logDTOList = new ArrayList<>();
        for (LogModel log : logModelList)
        {
            LogDTO logDTO = new LogDTO(log.getId(), log.getUserId(), log.getChangedTime(), log.getNewStatus());
            logDTOList.add(logDTO);
        }
        return logDTOList;
    }
}
