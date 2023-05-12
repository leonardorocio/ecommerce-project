package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductCategoryRequestBody {

    @NotNull
    @Schema(name = "Name", description = "ProductCategory's name", example = "CPU")
    String name;
}
