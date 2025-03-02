package com.example.rehabilitationandintegration.model.response;

import com.example.rehabilitationandintegration.model.CertificateDto;
import com.example.rehabilitationandintegration.model.SpecialtyDto;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SpecialistResponseDto {
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String education;
    private String graduate;
    private String language;
    private Integer experience;
}
