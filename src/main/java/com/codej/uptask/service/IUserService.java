package com.codej.uptask.service;


import com.codej.uptask.controller.dto.AuthResponse;
import com.codej.uptask.entity.UserEntity;
import com.codej.uptask.exception.UsuarioFoundException;

import java.util.Optional;

public interface IUserService {
    public UserEntity findById(Long id);

    public Optional<UserEntity>findByTokenPassword(String tokenPassword);
    public AuthResponse save (UserEntity usuario) throws UsuarioFoundException;
    public UserEntity update (UserEntity usuario) throws UsuarioFoundException;

    public UserEntity findByEmail(String email);
}
