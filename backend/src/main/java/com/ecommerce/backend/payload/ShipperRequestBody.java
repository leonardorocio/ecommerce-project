package com.ecommerce.backend.payload;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ShipperRequestBody {

    @NotNull
    @NotBlank
    @Schema(name = "Name", description = "The shipper's name",
            example = "Correios")
    private String name;

    @NotNull
    @Schema(name = "FixedTax", description = "The Shipper's fixed tax for it's shipments",
            example = "10.00")
    private double fixedTax;
}
