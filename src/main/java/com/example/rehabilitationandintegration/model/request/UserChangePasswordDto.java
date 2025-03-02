package com.example.rehabilitationandintegration.model.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserChangePasswordDto {

    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}",
            message = "PASSWORD MUST BE AT LEAST 6 CHARACTERS LONG AND CONTAIN BOTH LETTERS AND DIGITS")
    @NotBlank(message = "PASSWORD CANNOT BE EMPTY")
    private String oldPassword;

    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}",
            message = "PASSWORD MUST BE AT LEAST 6 CHARACTERS LONG AND CONTAIN BOTH LETTERS AND DIGITS")
    @NotBlank(message = "PASSWORD CANNOT BE EMPTY")
    private String newPassword;

    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}",
            message = "PASSWORD MUST BE AT LEAST 6 CHARACTERS LONG AND CONTAIN BOTH LETTERS AND DIGITS")
    @NotBlank(message = "PASSWORD CANNOT BE EMPTY")
    private String newPasswordConfirm;
}
