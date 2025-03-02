package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.OtpEntity;
import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.dao.repository.OtpRepository;
import com.example.rehabilitationandintegration.dao.repository.UserRepository;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
import com.example.rehabilitationandintegration.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpServiceImpl implements OtpService {
    private final SecureRandom random = new SecureRandom();
    private final OtpRepository otpRepository;
    private final UserRepository userRepository;

    @Override
    public OtpEntity generateOtp(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        String otp = String.valueOf(100000 + random.nextInt(900000));
        OtpEntity otpEntity = new OtpEntity();
        otpEntity.setOtp(otp);
        otpEntity.setUser(user);
        otpEntity.setExpirationTime(LocalDateTime.now().plusMinutes(5));
        otpRepository.save(otpEntity);
        return otpEntity;
    }

    @Override
    public boolean validateOtp(String email, String otp) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        OtpEntity otpEntity = otpRepository.findByUserAndOtp(user,  otp)
                .orElseThrow(()-> new ResourceNotFoundException("OTP not found or expired"));
        LocalDateTime now = LocalDateTime.now();
        return email.equals(otpEntity.getUser().getEmail()) && otp.equals(otpEntity.getOtp())
                && now.isBefore(otpEntity.getExpirationTime());
    }

}
