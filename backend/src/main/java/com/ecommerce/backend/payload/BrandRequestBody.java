package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrandRequestBody {

    @NotNull
    @NotBlank
    @Schema(name = "name", description = "Brand's name", example = "Nvidia")
    private String name;

    @NotNull
    @NotBlank
    @Schema(name = "brandLogoImage", description = "Brand's logo image", example = "firebase storage link", format = "binary")
    private String brandLogoImage;
}
