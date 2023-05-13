package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;

@Data
public class UserPatchRequestBody {

    @Schema(name = "name", description = "The user's name",
            example = "Fulano da Silva")
    private String name;

    @Schema(name = "birthDate", description = "The user's birth date",
            example = "1981-09-09")
    @NotNull
    private LocalDate birthDate;
}
