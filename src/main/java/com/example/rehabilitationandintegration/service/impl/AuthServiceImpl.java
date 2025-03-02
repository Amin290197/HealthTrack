package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.*;
import com.example.rehabilitationandintegration.dao.repository.*;
import com.example.rehabilitationandintegration.enums.Role;
import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.exception.InvalidOtpException;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
import com.example.rehabilitationandintegration.exception.TokenException;
import com.example.rehabilitationandintegration.exception.UserAlreadyExistsException;
import com.example.rehabilitationandintegration.mapper.UserMapper;
import com.example.rehabilitationandintegration.model.request.*;
import com.example.rehabilitationandintegration.model.response.JwtResponse;
import com.example.rehabilitationandintegration.service.AuthService;
import com.example.rehabilitationandintegration.service.MailService;
import com.example.rehabilitationandintegration.service.OtpService;
import com.example.rehabilitationandintegration.service.UserSecurityService;
import com.example.rehabilitationandintegration.utility.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserSecurityService userSecurityService;
    private final RoleRepository roleRepository;
    private final OtpService otpService;
    private final MailService mailService;


    public JwtResponse login(JwtRequest authenticationRequest) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Invalid username or password", e);
        }
        JwtResponse jwtResponse = jwtTokenUtil.createTokens(authenticationRequest);

        return jwtResponse;
    }

    //refresh token ucun
    public JwtResponse createRefreshToken(JwtRefreshRequestDto refreshRequestDto) throws Exception {
        String username = "";
        UserDetails userDetails1 = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof User) {
                userDetails1 = (User) principal;
                username = userDetails1.getUsername();
            }
        } else {
            throw new UsernameNotFoundException("User not found");
        }

        String refreshTokenRequestDto = refreshRequestDto.getRefreshToken();
        Claims claims = jwtTokenUtil.getClaimsFromToken(refreshTokenRequestDto);
        String usernameFromToken = claims.getSubject();
        UserDetails userDetails = userSecurityService.loadUserByUsername(usernameFromToken);

        // buna ehtiyac var ? validate ozu yoxlayir
//        if (!usernameFromToken.equals(userDetails1.getUsername())){
//            throw new Exception("Invalid username in JWT");
//        }

        if (jwtTokenUtil.validateToken(refreshTokenRequestDto, userDetails)) {
            return JwtResponse.builder().accessToken(jwtTokenUtil.createRefreshToken(claims, username))
                    .refreshToken(jwtTokenUtil.createRefreshToken(claims, username)).build();
        } else {
            throw new TokenException("Invalid refresh token");
        }

    }

    public void register(RegisterDto registerDto) {
        log.info("Action.register started for user {}", registerDto.getUsername());
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent() ||
                userRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists");
        }
        registerDto.setPassword(encoder.encode(registerDto.getPassword()));

        UserEntity userEntity = userMapper.toUserEntity(registerDto);
        userEntity.setStatus(Status.INACTIVE);

        List<RoleEntity> roleEntityList = new ArrayList<>();
        roleEntityList.add(roleRepository.findByName(Role.PATIENT));
        userEntity.setRoles(roleEntityList);
        userEntity.setRegistrationDate(LocalDateTime.now());

        userRepository.save(userEntity);

        mailService.registerRequestEmail(userEntity);
        log.info("Action.register ended for user {}", registerDto.getUsername());

    }


    @Override
    public void recoveryPassword(RecoveryPasswordDto recoveryPasswordDto) {
        log.info("Action.recoveryPassword ended for user {}", recoveryPasswordDto.getEmail());
        UserEntity userEntity = userRepository.findByEmail(recoveryPasswordDto.getEmail())
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        if (otpService.validateOtp(recoveryPasswordDto.getEmail(), recoveryPasswordDto.getOtp())){
            userEntity.setPassword(encoder.encode(recoveryPasswordDto.getPassword()));
            userRepository.save(userEntity);
        }
        else {
            throw new InvalidOtpException("Invalid OTP");
        }
    }

}
