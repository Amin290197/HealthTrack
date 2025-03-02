package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.OtpEntity;
import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<OtpEntity, Long> {
    Optional<OtpEntity> findByUserAndOtp(UserEntity user, String otp);
}
