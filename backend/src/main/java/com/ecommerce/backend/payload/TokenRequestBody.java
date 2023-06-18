package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class TokenRequestBody {

    @Schema(name = "refreshToken", description = "The user's valid RefreshToken",
            example = "951bdc11-3347-4b29-a9c4-bd15710798c5")
    private String refreshToken;
}
