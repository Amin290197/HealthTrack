package com.example.rehabilitationandintegration.model.response;

import com.example.rehabilitationandintegration.enums.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserForStatusDto {
    private Long id;
    private String username;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Status status;
}
