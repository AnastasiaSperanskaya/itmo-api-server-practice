package com.example.APIServer.controllers;

import com.example.APIServer.dtos.LogDTO;
import com.example.APIServer.dtos.UserDTO;
import com.example.APIServer.models.LogModel;
import com.example.APIServer.models.UserModel;
import com.example.APIServer.services.LogService;
import com.example.APIServer.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@Api(value = "UserController - Controller to access data linked with user")
public class UserController {

    private final UserService userService;
    private final LogService logService;

    @GetMapping("/user")
    @ApiOperation(value = "Get Users")
    public List<UserDTO> getUsers()
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

    @PostMapping("/user")
    @ApiOperation(value = "Create new user")
    public int createUser(@RequestBody UserDTO user)
    {
        return userService.create(new UserModel(user.getUsername(), user.getEmail(), user.getStatus()));
    }

    @GetMapping("/user/{id}")
    @ApiOperation(value = "Get user by ID")
    public UserDTO getUserById(@PathVariable(value = "id") Integer userId)
    {
        UserModel userModel = userService.findById(userId);
        return new UserDTO(userModel.getUsername(), userModel.getEmail(), userModel.getStatus());
    }

    @PutMapping("/user/{id}")
    @ApiOperation(value = "Change user status")
    public Map<String, Object> changeStatusById(@PathVariable(value = "id") Integer userId, @RequestParam(value = "status") String status)
    {
        return userService.changedStatus(userId, status);
    }

    @GetMapping("/logs")
    @ApiOperation(value = "Get history of logs")
    public List<LogDTO> getLogs()
    {
        List<LogModel> logModelList = logService.getAll();
        List<LogDTO> logDTOList = new ArrayList<>();
        for (LogModel log : logModelList)
        {
            LogDTO logDTO = new LogDTO(log.getId(), log.getUserId(), log.getChangedTime(), log.getChangedStatus());
            logDTOList.add(logDTO);
        }
        return logDTOList;
    }

    @GetMapping("/logs/{status}")
    @ApiOperation(value = "Get history of status after timestamp")
    public List<LogDTO> getLogsByTimestampAndStatus( @PathVariable(value = "status") String status, @RequestParam(value = "timestamp", required = false, defaultValue = "0") long timestamp)
    {
        List<LogModel> logModelList = logService.getAllByStatusAndTimestamp(timestamp,status);
        List<LogDTO> logDTOList = new ArrayList<>();
        for (LogModel log : logModelList)
        {
            LogDTO logDTO = new LogDTO(log.getId(), log.getUserId(), log.getChangedTime(), log.getChangedStatus());
            logDTOList.add(logDTO);
        }
        return logDTOList;
    }
}
