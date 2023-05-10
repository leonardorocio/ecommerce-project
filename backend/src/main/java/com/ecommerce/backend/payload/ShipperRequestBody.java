package com.ecommerce.backend.payload;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShipperRequestBody {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    private double fixedTax;
}
