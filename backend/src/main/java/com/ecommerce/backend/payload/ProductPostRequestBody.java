package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductPostRequestBody {

    @NotNull
    @NotBlank
    @Schema(name = "Name", description = "The name of the Product", example = "Intel Core i7")
    String name;

    @NotNull
    @NotBlank
    @Schema(name = "Description", description = "The description of the Product", example = "32 Threads and 8 Cores")
    String description;

    @NotNull
    @Schema(name = "Price", description = "The price of the Product", example = "1900.00")
    double price;

    @NotNull
    @Schema(name = "Stock", description = "The stock of the product", example = "10")
    int stock;

    @Schema(name = "DiscountMultiplier", description = "The discount multiplier starts from 1.0 (Full Price). " +
            "If it's 0.8, it means that the product is 80% it's original value, thus, 20% of discount", example = "0.8")
    double discount;

    @NotNull
    @Schema(name = "ProductCategory", description = "The ID of the Category related to the Product", example = "1")
    int category_id;
}
