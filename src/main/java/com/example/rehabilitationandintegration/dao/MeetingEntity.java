package com.example.rehabilitationandintegration.dao;

import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "meetings")
@Setter
@Getter
public class MeetingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    private LocalDate date;
    private LocalTime time;
    private LocalDateTime created;
    @Enumerated(EnumType.STRING)
    private MeetingAndAppointmentStatus status;
}
