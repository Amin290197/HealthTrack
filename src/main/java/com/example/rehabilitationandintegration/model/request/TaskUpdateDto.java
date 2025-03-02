package com.example.rehabilitationandintegration.model.request;

import com.example.rehabilitationandintegration.enums.TaskStatus;
import com.example.rehabilitationandintegration.validation.annotation.TaskStatusConstraint;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskUpdateDto {
    @NotBlank(message = "NAME MUST NOT BE BLANK")
    @Size(min = 3, max = 50, message = "NAME MUST BE BETWEEN 2 AND 50 CHARACTERS LONG")
    private String name;

    @NotNull(message = "START DATE CANNOT BE NULL")
    private LocalDate startDate;

    @NotNull(message = "END DATE CANNOT BE NULL")
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @TaskStatusConstraint(enumClass = TaskStatus.class)
    private TaskStatus status;
}
