package com.example.rehabilitationandintegration.model;

import com.example.rehabilitationandintegration.enums.Language;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.util.List;


@Data
public class SpecialistResponseForPatientDto {
    private Long id;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Language language;
    @Enumerated(EnumType.STRING)
    private SpecialtyDto specialty;
}
