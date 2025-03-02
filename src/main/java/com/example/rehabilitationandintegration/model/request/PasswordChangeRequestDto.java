package com.example.rehabilitationandintegration.model.request;

import lombok.Data;

@Data
public class PasswordChangeRequestDto {
    private String oldPassword;
    private String newPassword;
    private String newPasswordConfirm;
}
