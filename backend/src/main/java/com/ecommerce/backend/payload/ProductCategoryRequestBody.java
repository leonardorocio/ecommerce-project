package com.ecommerce.backend.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCategoryRequestBody {

    @NotNull
    @NotBlank
    String name;
}
