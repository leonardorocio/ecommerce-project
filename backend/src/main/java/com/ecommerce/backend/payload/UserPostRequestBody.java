package com.ecommerce.backend.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class UserPostRequestBody {

    @NotNull
    @NotBlank
    @Schema(name = "name", description = "The user's name",
            example = "Fulano da Silva")
    private String name;

    @NotNull
    @NotBlank
    @Schema(name = "email", description = "User's email", example = "test.testing2000@example.com", format = "email")
    private String email;

    @Schema(name = "birthDate", description = "The user's birth date",
            example = "1981-09-09")
    @NotNull
    private LocalDate birthDate;

    @NotNull
    @NotBlank
    @Schema(name = "password", description = "User's password", example = "example123", format = "password")
    private String password;

}
