package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
