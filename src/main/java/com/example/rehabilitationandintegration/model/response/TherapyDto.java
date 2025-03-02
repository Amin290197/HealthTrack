package com.example.rehabilitationandintegration.model.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class TherapyDto {
    private Long id;

    @NotBlank(message = "NAME MUST NOT BE BLANK")
    @Size(min = 3, max = 50, message = "NAME MUST BE BETWEEN 2 AND 50 CHARACTERS LONG")
    private String name;
}
