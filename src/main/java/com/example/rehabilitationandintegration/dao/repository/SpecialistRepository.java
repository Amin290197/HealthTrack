package com.example.rehabilitationandintegration.dao.repository;

import com.example.rehabilitationandintegration.dao.SpecialistEntity;
import com.example.rehabilitationandintegration.dao.SpecialtyEntity;
import com.example.rehabilitationandintegration.enums.Language;
import com.example.rehabilitationandintegration.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SpecialistRepository extends JpaRepository<SpecialistEntity, Long> {
    Optional<SpecialistEntity> findBySpecialty(SpecialtyEntity specialtyType);

    Optional<SpecialistEntity> findByIdAndStatus(Long id, Status status);

    @Query("SELECT s FROM SpecialistEntity s WHERE s.language = :language and s.specialty = :specialty")
    List<SpecialistEntity> findAllLanguageAndSpecialty(Language language, SpecialtyEntity specialty);
    Optional<SpecialistEntity> findByLanguage(Language language);

    Page<SpecialistEntity> findAllByStatus(Pageable pageable, Status status);

    Page<SpecialistEntity> findAllByStatusAndSpecialty(Pageable pageable, Status status, SpecialtyEntity specialty);
}
