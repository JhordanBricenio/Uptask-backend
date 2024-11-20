package com.codej.uptask.service.impl;

import com.codej.uptask.controller.dto.AuthResponse;
import com.codej.uptask.entity.Rol;
import com.codej.uptask.entity.UserEntity;
import com.codej.uptask.exception.UsuarioFoundException;
import com.codej.uptask.repository.IUserRepository;
import com.codej.uptask.service.IUserService;
import com.codej.uptask.util.JwtUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;


@Service
@AllArgsConstructor
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;
    private  PasswordEncoder passwordEncoder;

    private JwtUtils jwtProvider;

    @Override
    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<UserEntity> findByTokenPassword(String tokenPassword) {
        return userRepository.findByTokenPassword(tokenPassword);
    }

    @Override
    public AuthResponse save(UserEntity user) throws UsuarioFoundException {
        Optional<UserEntity> existingUser = userRepository.findByEmail(user.getEmail());
        if (user.getId()==null){
            if (existingUser.isPresent()) {
            throw new UsuarioFoundException("Email already registered");
            }
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Rol rol = new Rol();
        rol.setId(2L);
        rol.setName("USER");
        user.addRol(rol);
        userRepository.save(user);
        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(("ROLE_".concat(role.getName())))));
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()
                , authorities);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token= jwtProvider.createToken(authentication);
        AuthResponse authResponse= new AuthResponse(
                user.getName(),"User created successfully",token,true
        );
        return authResponse;
    }

    @Override
    public UserEntity update(UserEntity usuario) throws UsuarioFoundException {
        return userRepository.save(usuario);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
