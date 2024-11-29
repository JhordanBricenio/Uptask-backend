package com.codej.uptask.controller;


import com.codej.uptask.controller.dto.AuthLoginRequest;
import com.codej.uptask.controller.dto.AuthResponse;
import com.codej.uptask.entity.UserEntity;
import com.codej.uptask.exception.UsuarioFoundException;
import com.codej.uptask.security.UserDetailsServiceImpl;
import com.codej.uptask.service.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthRestController {

    @Autowired
    private UserDetailsServiceImpl userDetailService;

    @Autowired
    private IUserService userService;



    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid UserEntity user) throws UsuarioFoundException {
        return new ResponseEntity<>(this.userService.save(user), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

}