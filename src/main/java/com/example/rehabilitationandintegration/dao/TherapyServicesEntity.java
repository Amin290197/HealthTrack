package com.example.rehabilitationandintegration.dao;

import com.example.rehabilitationandintegration.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "therapy_services")
@Setter
@Getter
public class TherapyServicesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;
    @ManyToOne
    private TherapyEntity therapy;
}
