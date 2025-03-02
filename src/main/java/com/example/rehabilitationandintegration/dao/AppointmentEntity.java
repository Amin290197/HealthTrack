package com.example.rehabilitationandintegration.dao;

import com.example.rehabilitationandintegration.enums.MeetingAndAppointmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Setter
@Getter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;
    private Integer duration;
    @Enumerated(EnumType.STRING)
    private MeetingAndAppointmentStatus status;
    private Integer week;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;



    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private SpecialistEntity specialist;

}
