package com.example.rehabilitationandintegration.model.request;

import com.example.rehabilitationandintegration.validation.ValidationGroups;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskCreateDto {
    @NotBlank(message = "NAME MUST NOT BE BLANK")
    @Size(min = 3, max = 50, message = "NAME MUST BE BETWEEN 2 AND 50 CHARACTERS LONG")
    private String name;

    @NotNull(message = "START DATE CANNOT BE NULL")
    private LocalDate startDate;
}
