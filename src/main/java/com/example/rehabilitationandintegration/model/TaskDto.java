package com.example.rehabilitationandintegration.model;

import com.example.rehabilitationandintegration.enums.TaskStatus;
import com.example.rehabilitationandintegration.validation.ValidationGroups;
import com.example.rehabilitationandintegration.validation.annotation.TaskStatusConstraint;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskDto {

    @NotBlank(message = "NAME MUST NOT BE BLANK")
    @Size(min = 3, max = 50, message = "NAME MUST BE BETWEEN 3 AND 50 CHARACTERS LONG")
    private String name;
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;
    @FutureOrPresent(message = "End date must be in the future or present")
    private LocalDate endDate;
    @Enumerated(EnumType.STRING)
    @TaskStatusConstraint(enumClass = TaskStatus.class, message = "Status must not be null")
    private TaskStatus status;
    private String specialistName;
    private String therapy;
}
