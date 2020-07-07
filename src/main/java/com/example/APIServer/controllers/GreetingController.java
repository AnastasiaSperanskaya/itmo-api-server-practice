package com.example.APIServer.controllers;

import com.example.APIServer.entities.UserEntity;
import com.example.APIServer.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class GreetingController {
    @Autowired
    private UserRepository userRepo;

    @GetMapping
    public String main(Map<String, Object> model) {
        Iterable<UserEntity> users = userRepo.findAll();
        model.put("users", users);
        return "main";
    }

    @PostMapping
    public String add(@RequestParam String name, @RequestParam String email, Map<String, Object> model) {
        UserEntity user = new UserEntity(name, email);
        userRepo.save(user);
        Iterable<UserEntity> users = userRepo.findAll();
        model.put("users", users);

        return "main";
    }
}