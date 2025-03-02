package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import com.example.rehabilitationandintegration.dao.SpecialtyEntity;
import com.example.rehabilitationandintegration.enums.Language;
import com.example.rehabilitationandintegration.enums.SpecialtyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SpecialtyRepository extends JpaRepository<SpecialtyEntity, Long> {
    Optional<SpecialtyEntity> findByName(SpecialtyType name);

}
