package com.example.rehabilitationandintegration.controller;

import com.example.rehabilitationandintegration.model.request.*;
import com.example.rehabilitationandintegration.model.response.JwtResponse;
import com.example.rehabilitationandintegration.service.AuthService;
import com.example.rehabilitationandintegration.service.MailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final MailService mailService;

    @PostMapping("/sign-up")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDto registerDto){
        authService.register(registerDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest authenticationRequest) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(authService.login(authenticationRequest));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<JwtResponse> refreshToken(@RequestBody @Valid JwtRefreshRequestDto refreshRequestDto) throws Exception{
        return ResponseEntity.status(HttpStatus.OK).body(authService.createRefreshToken(refreshRequestDto));
    }

    //ResponseEntity[]

    @PostMapping("/forgotPassword")
    public ResponseEntity<Void> forgotPassword(@RequestBody @Valid EmailDto emailDto){
        mailService.recoveryPassword(emailDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/recoveryPassword")
    public ResponseEntity<Void> recoveryPassword(@RequestBody @Valid RecoveryPasswordDto recoveryPasswordDto) {
        authService.recoveryPassword(recoveryPasswordDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
