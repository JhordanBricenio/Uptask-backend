package com.codej.uptask.controller;

import com.codej.uptask.controller.dto.ChangePasswordDTO;
import com.codej.uptask.controller.dto.EmailValuesDTO;
import com.codej.uptask.controller.dto.MessageDTO;
import com.codej.uptask.entity.UserEntity;
import com.codej.uptask.exception.UsuarioFoundException;
import com.codej.uptask.service.EmailService;
import com.codej.uptask.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/email-password")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Autowired
    private IUserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${spring.mail.username}")
    private String mailFrom;

    private static final String subject="Cambio de contraseña";


   /* @GetMapping("/send")
    public ResponseEntity<?> sendEmail(){
        emailService.sendEmail();

        return new ResponseEntity<>("Correo enviado" +
                "correctamente", HttpStatus.OK);
    }*/

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO dto) throws UsuarioFoundException {
       UserEntity userEntity= userService.findByEmail(dto.getMailTo());
       if (userEntity==null){
           return new ResponseEntity<>(new MessageDTO("No existe ningún usuario con esas credenciales")
                   ,HttpStatus.NOT_FOUND);
       }
        dto.setMailFrom(mailFrom);
        dto.setSubject(subject);
        dto.setUserName(userEntity.getName());
        UUID uuid= UUID.randomUUID();
        String tokenPassword= uuid.toString();
        dto.setToken(tokenPassword);
        userEntity.setTokenPassword(tokenPassword);
        userService.update(userEntity);
        emailService.sendEmail(dto);
        return new ResponseEntity(new MessageDTO("Te hemos enviado un correo"), HttpStatus.OK);
    }


    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordDTO dto
            , BindingResult bindingResult) throws UsuarioFoundException {
        if (bindingResult.hasErrors()){
            return new ResponseEntity(new MessageDTO("Campos incorrectos"), HttpStatus.BAD_REQUEST);
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())){
            return new ResponseEntity(new MessageDTO("Las contraseñas no coinciden"), HttpStatus.BAD_REQUEST);
        }

        Optional<UserEntity> optionalUser= userService.findByTokenPassword(dto.getTokenPassword());
        if (!optionalUser.isPresent()){
            return new ResponseEntity(new MessageDTO("No existe ningun usuario con esas credenciales"), HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity= optionalUser.get();
        String newPassword= passwordEncoder.encode(dto.getPassword());
        userEntity.setPassword(newPassword);
        userEntity.setTokenPassword(null);
        userService.update(userEntity);

        return new ResponseEntity<>(new MessageDTO("Contraseña actualizada"), HttpStatus.OK);
    }








}
