package com.ecommerce.backend.payload;

import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCategoryRequestBody {

    @NotNull
    @NotBlank
    @Schema(name = "name", description = "ProductCategory's name", example = "CPU")
    String name;
}
