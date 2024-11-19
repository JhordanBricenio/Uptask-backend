package com.codej.uptask.service;


import com.codej.uptask.controller.dto.AuthResponse;
import com.codej.uptask.entity.UserEntity;
import com.codej.uptask.exception.UsuarioFoundException;

public interface IUserService {
    public UserEntity findById(Integer id);
    public AuthResponse save (UserEntity usuario) throws UsuarioFoundException;

    public UserEntity findByEmail(String email);
}
