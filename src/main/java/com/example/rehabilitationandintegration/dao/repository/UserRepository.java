package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
    Page<UserEntity> findByStatus(Status status, Pageable pageable);

    Page<UserEntity> findAll(Specification specification, Pageable pageable);
}
