package com.ecommerce.backend.payload;

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
    String email;

    @NotNull
    @NotBlank
    String password;

}
