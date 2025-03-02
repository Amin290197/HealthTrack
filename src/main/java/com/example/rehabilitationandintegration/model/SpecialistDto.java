package com.example.rehabilitationandintegration.model;

import com.example.rehabilitationandintegration.enums.Status;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class SpecialistDto {

    private Long id;
    private String name;
    private String surname;

    @Past(message = "BIRTH DATE MUST BE IN THE PAST")
    private LocalDate birthDate;
    private String education;
    private String graduate;
    private String language;

    @PositiveOrZero(message = "EXPERIENCE CANNOT BE NEGATIVE")
    private Integer experience;

    private LocalDateTime registrationDate;
    private Status status;

    private SpecialtyDto specialty;
    private List<ScheduleDto> schedule;
}
