package com.codej.uptask.security;

import com.codej.uptask.controller.dto.AuthLoginRequest;
import com.codej.uptask.controller.dto.AuthResponse;
import com.codej.uptask.entity.UserEntity;
import com.codej.uptask.repository.IUserRepository;
import com.codej.uptask.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private IUserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));

        List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();

        userEntity.getRoles().forEach(role -> authoritiesList.add(new SimpleGrantedAuthority(("ROLE_".concat(role.getName())))));
        return new User(userEntity.getName(), userEntity.getPassword(),
                userEntity.getEnabled(), userEntity.getAccountNoExpired(), userEntity.getCredentialsNoExpired(),
                userEntity.getAccountNoLocked(), authoritiesList);
    }

    public AuthResponse loginUser(AuthLoginRequest authLoginRequest) {

        String username = authLoginRequest.email();
        String password = authLoginRequest.password();

        Authentication authentication = this.authenticate(username, password);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.createToken(authentication);
        AuthResponse authResponse = new AuthResponse(username, "User loged succesfully", accessToken, true);
        return authResponse;
    }

    public Authentication authenticate(String email, String password) {
        UserDetails userDetails = this.loadUserByUsername(email);

        if (userDetails == null) {
            throw new BadCredentialsException(String.format("Invalid username or password"));
        }

        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }

        return new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());
    }
}
