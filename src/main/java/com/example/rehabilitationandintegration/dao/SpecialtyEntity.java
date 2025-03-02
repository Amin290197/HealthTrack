package com.example.rehabilitationandintegration.dao;

import com.example.rehabilitationandintegration.enums.SpecialtyType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "specialties")
@Setter
@Getter
public class SpecialtyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private SpecialtyType name;

}
