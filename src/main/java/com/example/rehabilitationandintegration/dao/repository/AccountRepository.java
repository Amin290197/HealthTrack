package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    Optional<AccountEntity> findByCardNumberAndCvvAndExpiryDate(String cardNumber, Integer cvv, LocalDate expiryDate);
}
