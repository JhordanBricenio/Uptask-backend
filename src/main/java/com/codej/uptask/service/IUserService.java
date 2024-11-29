package com.codej.uptask.service;


import com.codej.uptask.controller.dto.AuthResponse;
import com.codej.uptask.entity.UserEntity;
import com.codej.uptask.exception.UsuarioFoundException;

import java.util.Optional;

public interface IUserService {
    UserEntity findById(Long id);

    Optional<UserEntity>findByTokenPassword(String tokenPassword);
    AuthResponse save (UserEntity usuario) throws UsuarioFoundException;
    UserEntity update (UserEntity usuario) throws UsuarioFoundException;

    UserEntity findUserProfileByJwt(String jwt);

    UserEntity findByEmail(String email);
}
