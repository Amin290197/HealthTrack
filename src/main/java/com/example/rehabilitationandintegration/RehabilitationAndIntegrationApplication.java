package com.example.rehabilitationandintegration;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import javax.crypto.SecretKey;
import java.util.Base64;

@SpringBootApplication
@EnableWebSecurity
@EnableScheduling
public class RehabilitationAndIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RehabilitationAndIntegrationApplication.class, args);
        SecretKey key = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);
        String encodedKey = Base64.getEncoder().encodeToString(key.getEncoded());
        System.out.println("New secretKey: " + encodedKey);

        String newKey = Base64.getEncoder().encodeToString(Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded());
        System.out.println(newKey);

        byte[] keyBytes = Base64.getDecoder().decode(newKey);
        System.out.println("Decoded key length: " + keyBytes.length);
    }

}
