package com.example.rehabilitationandintegration.model.request;


import com.example.rehabilitationandintegration.enums.Gender;
import com.example.rehabilitationandintegration.enums.Language;
import com.example.rehabilitationandintegration.validation.annotation.GenderConstraint;
import com.example.rehabilitationandintegration.validation.annotation.LanguageConstraint;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
public class RegisterDto {
    @Pattern(regexp = "^[a-zA-Z0-9]{3,}$",
            message = "USERNAME MUST BE AT LEAST 3 CHARACTERS LONG AND CONTAIN ONLY LETTERS AND DIGITS")
    @NotBlank(message = "USERNAME CANNOT BE EMPTY")
    private String username;

    @Pattern(regexp = "(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{6,}",
            message = "PASSWORD MUST BE AT LEAST 6 CHARACTERS LONG AND CONTAIN BOTH LETTERS AND DIGITS")
    @NotBlank(message = "PASSWORD CANNOT BE EMPTY")
    private String password;

    @NotBlank(message = "EMAIL CANNOT BE EMPTY")
    @Email(message = "INVALID EMAIL ADDRESS")
    private String email;

    @Pattern(regexp = "^[A-Za-z]{3,}$",
            message = "NAME MUST CONTAIN AT LEAST 3 LETTERS AND ONLY LATIN LETTERS")
    @NotBlank(message = "NAME CANNOT BE EMPTY")
    private String name;

    @Pattern(regexp = "^[A-Za-z]{3,}$",
            message = "SURNAME MUST CONTAIN AT LEAST 3 LETTERS AND ONLY LATIN LETTERS")
    @NotBlank(message = "SURNAME CANNOT BE EMPTY")
    private String surname;

    @NotNull(message = "BIRTH DATE CANNOT BE NULL")
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @Past(message = "BIRTH DATE MUST BE IN THE PAST")
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @GenderConstraint(enumClass = Gender.class)
    private Gender gender;

    @Pattern(regexp = "^\\+994(50|51|55|70|77|99)[0-9]{7}$",
            message = "PHONE NUMBER MUST BE A VALID AZERBAIJAN PHONE NUMBER")
    @NotBlank(message = "PHONE NUMBER CANNOT BE EMPTY")
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @LanguageConstraint(enumClass = Language.class)
    private Language language;

    @NotBlank(message = "NOTE CANNOT BE NULL OR BLANK")
    private String note;

}