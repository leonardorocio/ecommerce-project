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
    private String name;

    @NotNull
    @NotBlank
    @Schema(name = "categoryImage", description = "ProductCategory's image", example = "https://firebasestorage.googleapis.com/v0/b/portfolio-project-e9246.appspot.com/o/shutterstock_2082475195.webp?alt=media&token=24595f07-be69-42a6-82c3-0f08e3044d9c&_gl=1*dvce9z*_ga*MTc0OTUwMTQ1OS4xNjg0MDIxMDcw*_ga_CW55HF8NVT*MTY4NTY0MTMwNC4yLjEuMTY4NTY0MTUyMi4wLjAuMA..")
    private String categoryImage;
}
