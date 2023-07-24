package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddressRequestBody {

    @NotNull
    @NotBlank
    @Schema(name = "zipCode", description = "Address's zipcode", example = "28614160", maxLength = 9)
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
    @Schema(name = "street", description = "Address's Street", example = "Rua Jair Correa")
    private String street;

    @NotNull
    @NotBlank
    @Schema(name = "complement", description = "Address's Complement", example = "Próximo à igreja")
    private String complement;

    @NotNull
    @Schema(name = "number", description = "Address's street's number", example = "130")
    private int number;

    @NotNull
    @Schema(name = "userId", description = "UserId from the Address owner", example = "1", ref = "User")
    private int userId;
}
