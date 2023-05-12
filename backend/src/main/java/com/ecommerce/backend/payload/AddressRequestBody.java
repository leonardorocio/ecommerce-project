package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequestBody {

    @NotNull
    @NotBlank
    @Schema(name = "zipCode", description = "Address's zipcode", example = "28614160")
    private String zipCode;

    @NotNull
    @NotBlank
    @Schema(name = "city", description = "Address's City name", example = "Rio de Janeiro")
    private String city;

    @NotNull
    @NotBlank
    @Schema(name = "state", description = "Address's State name", example = "Pernambuco")
    private String state;

    @NotNull
    @NotBlank
    @Schema(name = "streetWithNumber", description = "Address's Street with the number", example = "Rua Jair Correa, 130")
    private String streetWithNumber;

    @NotNull
    @Schema(name = "userId", description = "UserId from the Address owner", example = "1")
    private int userId;
}
