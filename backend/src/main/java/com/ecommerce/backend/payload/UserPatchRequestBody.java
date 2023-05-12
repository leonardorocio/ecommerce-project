package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
public class UserPatchRequestBody {

    @Schema(name = "name", description = "The user's name",
            example = "Fulano da Silva")
    String name;

    @Schema(name = "birthDate", description = "The user's birth date",
            example = "1981-09-09")
    Date birthDate;
}
