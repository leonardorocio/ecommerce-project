package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequestBody {

    @NotNull
    @NotBlank
    @Schema(name = "ZipCode", description = "Address's zipcode", example = "28614160")
    private String zipCode;

    @NotNull
    @NotBlank
    @Schema(name = "City", description = "Address's City name", example = "Rio de Janeiro")
    private String city;

    @NotNull
    @NotBlank
    @Schema(name = "State", description = "Address's State name", example = "Pernambuco")
    private String state;

    @NotNull
    @NotBlank
    @Schema(name = "Street", description = "Address's Street with the number", example = "Rua Jair Correa, 130")
    private String streetWithNumber;

    @NotNull
    @Schema(name = "UserId", description = "UserId from the Address owner", example = "1")
    private int userId;
}
