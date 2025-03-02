package com.example.rehabilitationandintegration.model.request;

import lombok.Data;

@Data
public class RecoveryPasswordDto {
    private String email;
    private String otp;
    private String password;
    private String confirmPassword;
}
