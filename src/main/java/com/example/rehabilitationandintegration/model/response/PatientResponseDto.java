package com.example.rehabilitationandintegration.model.response;

import com.example.rehabilitationandintegration.dao.UserEntity;
import com.example.rehabilitationandintegration.enums.Gender;
import com.example.rehabilitationandintegration.enums.Language;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class PatientResponseDto {
    private Long id;
    private String name;
    private String surname;
    private LocalDate birthDate;
    private Gender gender;
    private String phoneNumber;
    private Language language;
    private LocalDateTime registrationDate;
    private String note;
}
