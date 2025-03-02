package com.example.rehabilitationandintegration.dao;

import com.example.rehabilitationandintegration.enums.TaskStatus;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Setter
@Getter
@EqualsAndHashCode
public class TaskEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    private TaskStatus status;
    @ManyToOne
    private UserEntity user;
    @ManyToOne
    private SpecialistEntity specialist;


}
