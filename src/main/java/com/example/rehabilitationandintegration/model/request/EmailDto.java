package com.example.rehabilitationandintegration.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class EmailDto {
    @NotBlank(message = "EMAIL CANNOT BE EMPTY")
    @Email(message = "INVALID EMAIL ADDRESS")
    private String email;
}
