package com.example.rehabilitationandintegration.model.response;

import com.example.rehabilitationandintegration.model.SpecialtyDto;
import lombok.Data;

@Data
public class SpecialistAllResponseDto {
    private Long id;
    private String name;
    private String surname;
    private SpecialtyDto specialty;
}
