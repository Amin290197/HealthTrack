package com.example.rehabilitationandintegration.service.impl;

import com.example.rehabilitationandintegration.dao.OtpEntity;
import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.dao.repository.UserRepository;
import com.example.rehabilitationandintegration.exception.ResourceNotFoundException;
import com.example.rehabilitationandintegration.model.request.AppointmentRequestList;
import com.example.rehabilitationandintegration.model.request.EmailDto;
import com.example.rehabilitationandintegration.service.MailService;
import com.example.rehabilitationandintegration.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender mailSender;
    private final UserRepository userRepository;
    private final OtpService otpService;

    @Value("${spring.mail.username}")
    private String from;
    SimpleMailMessage mailMessage = new SimpleMailMessage();


    public void registrationMessage(EmailDto emailDto) {
        UserEntity user = userRepository.findByEmail(emailDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        mailMessage.setTo(emailDto.getEmail());
        mailMessage.setSubject("Hello World!");
        mailMessage.setText("\n" +
                "Thank you for your request! We will notify you if your request has been accepted!");
        mailMessage.setFrom(from);
        mailSender.send(mailMessage);
    }

    @Transactional
    @Override
    public void recoveryPassword(EmailDto emailDto) {
        log.info("Action.log.recoveryPassword started for user {}", emailDto.getEmail());
        UserEntity user = userRepository.findByEmail(emailDto.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        OtpEntity otp = otpService.generateOtp(emailDto.getEmail());
        mailMessage.setTo(emailDto.getEmail());
        mailMessage.setSubject("OTP CODE");
        mailMessage.setText("\n" +
                "Hello, " + user.getName() +
                "\n" +
                "\n" +
                "Use the code below to log back into your account. Rehabilitation." +
                "\n" +
                "\n" +
                otp.getOtp() +
                "\n" +
                "\n" +
                "\n" +
                "This code expires in 5 minutes.");
        mailMessage.setFrom(from);
        try {
            mailSender.send(mailMessage);
        } catch (Exception e) {
            throw new RuntimeException("Email couldn't sent. Try again.");
        }
        log.info("Action.log.recoveryPassword ended for user {}", emailDto.getEmail());
    }

    public void sendActiveEmail(UserEntity user){
        log.info("Action.log.sendActiveEmail started for user {}", user.getId());
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Congratulations " + user.getName() + " " + user.getSurname());
        mailMessage.setText("Your profile already active");
        mailMessage.setFrom(from);
        try {
            mailSender.send(mailMessage);
        } catch (Exception e) {
            throw new RuntimeException("Email couldn't sent. Try again.");
        }
    }

    @Override
    public void registerRequestEmail(UserEntity user) {
        log.info("Action.log.registerRequestEmail started for user {}", user.getId());
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Hi, " + user.getName() + " " +user.getSurname() + ".");
        mailMessage.setText("Thank you! Your request has been recorded. You will be notified of this email.");
        mailMessage.setFrom(from);
        try {
            mailSender.send(mailMessage);
        } catch (Exception e) {
            throw new RuntimeException("Email couldn't sent. Try again.");
        }
    }

    @Override
    public void appointmentNotify(UserEntity user, Double sum) {
        log.info("Action.log.appointmentTime started for user {}", user.getId());
        mailMessage.setTo(user.getEmail());
        mailMessage.setSubject("Congratulations!");
        mailMessage.setText("You have successfully registered for the lessons! ✅\n" +
                "Now you can track your schedule and access all important information through your personal account.\n" +
                "Total price : " + sum);
        mailMessage.setFrom(from);
        try {
            mailSender.send(mailMessage);
        } catch (Exception e) {
            throw new RuntimeException("Email couldn't sent. Try again.");
        }
    }
}
