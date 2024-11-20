package com.codej.uptask.controller;

import com.codej.uptask.controller.dto.EmailValuesDTO;
import com.codej.uptask.controller.dto.MessageDTO;
import com.codej.uptask.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/email-password")
public class EmailController {

    @Autowired
    EmailService emailService;

    @Value("${spring.mail.username}")
    private String mailFrom;


   /* @GetMapping("/send")
    public ResponseEntity<?> sendEmail(){
        emailService.sendEmail();

        return new ResponseEntity<>("Correo enviado" +
                "correctamente", HttpStatus.OK);
    }*/

    @PostMapping("/send-email")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO dto){

        dto.setMailFrom(mailFrom);
        dto.setSubject("Cambio de contraseña");
        dto.setUserName("Juan");
        UUID uuid= UUID.randomUUID();
        String tokenPassword= uuid.toString();
        dto.setToken(tokenPassword);
        emailService.sendEmail(dto);
        return new ResponseEntity(new MessageDTO("Te hemos enviado un correo"), HttpStatus.OK);
    }
}
