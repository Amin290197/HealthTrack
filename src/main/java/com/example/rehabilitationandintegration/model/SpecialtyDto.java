package com.example.rehabilitationandintegration.model;

import com.example.rehabilitationandintegration.enums.SpecialtyType;
import jakarta.persistence.*;
import lombok.Data;

@Data
public class SpecialtyDto {

    private Long id;
    private SpecialtyType name;
}
