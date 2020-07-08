package com.example.APIServer.controllers;

import com.example.APIServer.dtos.UserDTO;
import com.example.APIServer.models.UserModel;
import com.example.APIServer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public Map<String, Long> addUser(@RequestBody UserDTO userDTO) {
        UserModel userModel = new UserModel(
                userDTO.getUsername(),
                userDTO.getEmail()
        );

        userModel = userService.addUser(userModel);
        userDTO.setId(userModel.getId());

        return Collections.singletonMap("id", userDTO.getId());
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) {
        UserModel userModel = userService.getUser(id);

        UserDTO userDTO = new UserDTO(
                userModel.getUsername(),
                userModel.getEmail()
        );

        return userDTO;
    }
}
