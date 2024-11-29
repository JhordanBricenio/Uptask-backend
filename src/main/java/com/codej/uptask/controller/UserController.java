package com.codej.uptask.controller;


import com.codej.uptask.entity.UserEntity;
import com.codej.uptask.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    private final IUserService userService;


    @GetMapping("/user/findByEmail/{email}")
    UserEntity findByEmail(@PathVariable String email){
        return userService.findByEmail(email);
    }
}
