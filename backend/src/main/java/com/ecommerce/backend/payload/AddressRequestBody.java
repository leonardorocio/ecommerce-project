package com.ecommerce.backend.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequestBody {

    @NotNull
    @NotBlank
    private String zipCode;

    @NotNull
    @NotBlank
    private String city;

    @NotNull
    @NotBlank
    private String state;

    @NotNull
    @NotBlank
    private String streetWithNumber;

    @NotNull
    private int userId;
}
