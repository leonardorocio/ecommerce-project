package com.ecommerce.backend.payload;


import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class UserPostRequestBody {

    @NotNull
    @NotBlank
    String name;

    @NotNull
    @NotBlank
    @Column(unique = true)
    String email;

    Date date;

    String cep;

    @NotNull
    @NotBlank
    String password;

}
