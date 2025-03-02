package com.example.rehabilitationandintegration.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CertificateDto {

    private Long id;

    @NotBlank(message = "NAME MUST NOT BE BLANK")
    @Size(min = 3, max = 50, message = "NAME MUST BE BETWEEN 2 AND 50 CHARACTERS LONG")
    private String name;

    @NotNull(message = "DATE CANNOT BE NULL")
    private LocalDate date;
}
