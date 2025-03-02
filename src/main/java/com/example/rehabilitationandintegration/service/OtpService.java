package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.dao.OtpEntity;

public interface OtpService {
    OtpEntity generateOtp(String email);

    boolean validateOtp(String email, String otp);
}
