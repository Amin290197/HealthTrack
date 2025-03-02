package com.example.rehabilitationandintegration.model.request;

import com.example.rehabilitationandintegration.enums.Language;
import com.example.rehabilitationandintegration.enums.SpecialtyType;
import com.example.rehabilitationandintegration.model.ScheduleDto;
import com.example.rehabilitationandintegration.validation.ValidationGroups;
import com.example.rehabilitationandintegration.validation.annotation.LanguageConstraint;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Validation;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class SpecialistCreateUpdateDto {

    private Long id;

    @NotBlank(groups = ValidationGroups.Create.class, message = "NAME MUST NOT BE BLANK")
    @Size(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class},
            min = 3, max = 50, message = "NAME MUST BE BETWEEN 2 AND 50 CHARACTERS LONG")
    private String name;

    @NotBlank(groups = ValidationGroups.Create.class, message = "SURNAME MUST NOT BE BLANK")
    @Size(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class},
            min = 3, max = 50, message = "SURNAME MUST BE BETWEEN 2 AND 50 CHARACTERS LONG")
    private String surname;

    @Past(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class},
            message = "BIRTH DATE MUST BE IN THE PAST")
    private LocalDate birthDate;

    @NotBlank(groups = ValidationGroups.Create.class, message = "EDUCATION MUST NOT BE BLANK")
    @Size(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class},
            min = 3, max = 100, message = "EDUCATION MUST BE BETWEEN 2 AND 100 CHARACTERS LONG")
    private String education;

    @NotBlank(groups = ValidationGroups.Create.class, message = "GRADUATE MUST NOT BE BLANK")
    @Size(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class},
            min = 3, max = 100, message = "GRADUATE MUST BE BETWEEN 2 AND 100 CHARACTERS LONG")
    private String graduate;

    @NotNull(groups = ValidationGroups.Create.class, message = "LANGUAGE MUST NOT BE NULL")
    @LanguageConstraint(enumClass = Language.class)
    private Language language;

    @NotNull(groups = ValidationGroups.Create.class, message = "EXPERIENCE MUST NOT BE NULL")
    @Min(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class},
            value = 0, message = "EXPERIENCE MUST BE AT LEAST 0 YEARS")
    @Max(groups = {ValidationGroups.Create.class, ValidationGroups.Update.class},
            value = 50, message = "EXPERIENCE MUST NOT EXCEED 65 YEARS")
    private Integer experience;

    @Enumerated(EnumType.STRING)
    private SpecialtyType specialty;

//    @NotNull(groups = ValidationGroups.Create.class, message = "SCHEDULE MUST NOT BE NULL")
//    private List<ScheduleDto> schedule;

}
