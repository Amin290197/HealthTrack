package com.example.rehabilitationandintegration.model.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AccountRequestDto {
    @Size(max = 16, min = 16, message = "CARD NUMBER MUST BE CONSIST OF 16 NUMBERS")
    @NotBlank(message = "CARD NUMBER MUST BE CONSIST OF 16 NUMBERS")
    private String cardNumber;
    
//    private Double balance;

    @Min(value = 100, message = "CVV MUST BE 3 DIGITS")
    @Max(value = 999, message = "CVV MUST BE 3 DIGITS")
    @NotNull(message = "CARD CVV MUST BE CONSIST OF 3 NUMBERS")
    private Integer cvv;

    @Future(message = "EXPIRY DATE MUST BE FUTURE DATE")
    private LocalDate expiryDate;
}
