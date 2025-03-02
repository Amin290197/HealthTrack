package com.example.rehabilitationandintegration.dao;

import com.example.rehabilitationandintegration.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "otp")
@Setter
@Getter
public class OtpEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private UserEntity user;
    private String otp;
    private LocalDateTime expirationTime;
}
