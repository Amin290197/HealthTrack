package com.example.rehabilitationandintegration.dao;

import com.example.rehabilitationandintegration.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "certificates")
@Getter
@Setter
public class CertificateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private SpecialistEntity specialist;
}
