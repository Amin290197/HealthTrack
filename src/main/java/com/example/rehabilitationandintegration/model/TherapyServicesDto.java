package com.example.rehabilitationandintegration.model;

import com.example.rehabilitationandintegration.enums.Status;
import com.example.rehabilitationandintegration.validation.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TherapyServicesDto {
    private Long id;

    @NotBlank(message = "NAME MUST NOT BE BLANK")
    @Size(min = 2, max = 50, message = "NAME MUST BE BETWEEN 2 AND 50 CHARACTERS LONG")
    private String name;
//    @Enumerated(EnumType.STRING)
//    private Status status;
//    private TherapyDto therapy;
}
