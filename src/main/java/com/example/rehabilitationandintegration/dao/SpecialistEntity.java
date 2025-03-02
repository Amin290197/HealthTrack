package com.example.rehabilitationandintegration.dao;

import com.example.rehabilitationandintegration.enums.Language;
import com.example.rehabilitationandintegration.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "specialists")
@Setter
@Getter
public class SpecialistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private String education;
    private String graduate;
    @Enumerated(EnumType.STRING)
    private Language language;
    private Integer experience;
    private LocalDateTime registrationDate;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialist_id")
    private List<ScheduleEntity> schedule;



    //cascade = {CascadeType.PERSIST, CascadeType.MERGE}
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "specialty_id")
    private SpecialtyEntity specialty;

    //(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "appointments",
            joinColumns = @JoinColumn(name = "specialist_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<UserEntity> user;


    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "specialist_id")
    private List<CertificateEntity> certificate;

    @OneToMany
    @JoinColumn(name = "specialist_id")
    private List<TaskEntity> tasks;
}
