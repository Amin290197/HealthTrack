package com.example.rehabilitationandintegration.dao;

import com.example.rehabilitationandintegration.enums.DayOfWeekEnum;
import com.example.rehabilitationandintegration.enums.Status;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Table(name = "schedules")
@Setter
@Getter
public class ScheduleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private SpecialistEntity specialist;
    private Integer day;
    private LocalTime startTime;
    private LocalTime endTime;
    @Enumerated(EnumType.STRING)
    private Status status;
}
