package com.example.rehabilitationandintegration.service;

import com.example.rehabilitationandintegration.model.request.*;
import com.example.rehabilitationandintegration.model.response.JwtResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public interface AuthService {

    void register(RegisterDto registerDto);

    JwtResponse login(JwtRequest authenticationRequest) throws Exception;

    JwtResponse createRefreshToken(JwtRefreshRequestDto refreshRequest) throws Exception;

    void recoveryPassword(RecoveryPasswordDto recoveryPasswordDto);
}