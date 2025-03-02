package com.example.rehabilitationandintegration.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class JwtRefreshRequestDto {
    @NotBlank(message = "TOKEN CANNOT BE EMPTY")
    private String refreshToken;
}
