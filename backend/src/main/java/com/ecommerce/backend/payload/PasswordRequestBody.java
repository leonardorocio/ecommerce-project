package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordRequestBody {

    @NotNull
    @NotBlank
    @Schema(name = "email", description = "User's email", example = "test.testing2000@example.com")
    String email;

    @NotNull
    @NotBlank
    @Schema(name = "password", description = "User's password", example = "example123")
    String password;

}
