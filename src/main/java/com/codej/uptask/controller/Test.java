package com.codej.uptask.controller;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;


@Component // Esto indica que la clase será gestionada por Spring
public class Test {

    public static String generateSecretKey() {
        // Crea un objeto SecureRandom para generar números aleatorios
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[32];  // 32 bytes = 256 bits
        secureRandom.nextBytes(key);

        // Convierte la clave en una cadena Base64 para que sea legible
        return Base64.getEncoder().encodeToString(key);
    }

    public static void main(String[] args) {
        String secretKey = generateSecretKey();
        System.out.println("Generated secret key: " + secretKey);
    }
}
