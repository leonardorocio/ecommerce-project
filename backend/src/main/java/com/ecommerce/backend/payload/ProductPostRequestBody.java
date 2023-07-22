package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductPostRequestBody {

    @NotNull
    @NotBlank
    @Schema(name = "name", description = "The name of the Product", example = "Intel Core i7")
    private String name;

    @NotNull
    @NotBlank
    @Schema(name = "shortDescription", description = "The short description of the Product", example = "32 Threads and 8 Cores")
    private String shortDescription;

    @NotNull
    @NotBlank
    @Schema(name = "detailedDescription", description = "The detailed description of the Product", example = "32 Threads and 8 Cores...")
    private String detailedDescription;

    @NotNull
    @Schema(name = "price", description = "The price of the Product", example = "1900.00")
    private double price;

    @NotNull
    @Schema(name = "stock", description = "The stock of the product", example = "10")
    private int stock;

    @Schema(name = "discount", description = "The discount multiplier starts from 1.0 (Full Price). " +
            "If it's 0.8, it means that the product is 80% it's original value, thus, 20% of discount", example = "0.8")
    private double discount;

    @NotNull
    @Schema(name = "productCategory", description = "The ID of the Category related to the Product", example = "1", ref = "ProductCategory")
    private int productCategory;

    @NotNull
    @Schema(name = "brand", description = "The ID of the Brand related to the Product", example = "1", ref = "Brand")
    private int brand;

    @NotNull
    @Schema(name = "productImage", description = "The link to access the image",
            example = "https://firebasestorage.googleapis.com/v0/b/portfolio-project-e9246.appspot.com/o/shutterstock_2082475195.webp?alt=media&token=24595f07-be69-42a6-82c3-0f08e3044d9c&_gl=1*dvce9z*_ga*MTc0OTUwMTQ1OS4xNjg0MDIxMDcw*_ga_CW55HF8NVT*MTY4NTY0MTMwNC4yLjEuMTY4NTY0MTUyMi4wLjAuMA..",
            format = "byte")
    private String productImage;
}
