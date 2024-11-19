package com.codej.uptask.controller;

import com.codej.uptask.controller.dto.EmailValuesDTO;
import com.codej.uptask.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    EmailService emailService;


    @GetMapping("/send")
    public ResponseEntity<?> sendEmail(){
        emailService.sendEmail();

        return new ResponseEntity<>("Correo enviado" +
                "correctamente", HttpStatus.OK);
    }

    @PostMapping("/send-template")
    public ResponseEntity<?> sendEmailTemplate(@RequestBody EmailValuesDTO dto){

        emailService.sendEmailTemplate(dto);
        return new ResponseEntity<>("Email con plantilla enviado " +
                "correctamente", HttpStatus.OK);
    }
}
