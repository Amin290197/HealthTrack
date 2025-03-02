package com.example.rehabilitationandintegration.model.request;

import com.example.rehabilitationandintegration.enums.Language;
import com.example.rehabilitationandintegration.model.SpecialtyDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class SpecialistUpdateDto {
    private Long id;
    private String name;
    private String surname;
    private String education;
    private String graduate;
    @Enumerated(EnumType.STRING)
    private Language language;
    private Integer experience;

    private SpecialtyDto specialty;
}
