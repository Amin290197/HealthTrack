package com.example.rehabilitationandintegration.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class JwtRequest {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$",
            message = "USERNAME MUST BE AT LEAST 3 CHARACTERS LONG AND CONTAIN ONLY LETTERS AND DIGITS")
    @NotBlank(message = "USERNAME CANNOT BE EMPTY")
    private String username;

    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}",
            message = "PASSWORD MUST BE AT LEAST 6 CHARACTERS LONG AND CONTAIN BOTH LETTERS AND DIGITS")
    @NotBlank(message = "PASSWORD CANNOT BE EMPTY")
    private String password;

//    @NotBlank(message = "EMAIL CANNOT BE EMPTY")
//    @Email(message = "INVALID EMAIL ADDRESS")
//    private String email;
}
