package com.ecommerce.backend.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductPostRequestBody {

    @NotNull
    @NotBlank
    String name;

    @NotNull
    @NotBlank
    String description;

    @NotNull
    double price;

    @NotNull
    int stock;

    @NotNull
    double discount;

    @NotNull
    int category_id;
}
