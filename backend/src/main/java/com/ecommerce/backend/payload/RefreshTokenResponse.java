package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RefreshTokenResponse {

    @Schema(name = "accessToken",
            description = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJmb25zby5mZWRlN0BnbWFpbC5jb20iLCJpYX" +
                    "QiOjE2ODM3Mjk3NTcsImV4cCI6MTY4MzczMTE5N30.z6hYKnen38bsBm01Gs-EZOVk8PP6H9AdSG9q1qWZ3lA",
            example = "951bdc11-3347-4b29-a9c4-bd15710798c5")
    private String accessToken;

    @Schema(name = "refreshToken", description = "The user's RefreshToken",
            example = "951bdc11-3347-4b29-a9c4-bd15710798c5")
    private String refreshToken;

    @Schema(name = "tokenType", description = "The type of the Token, will always be 'Bearer'",
            example = "Bearer")
    private String tokenType = "Bearer";

    public RefreshTokenResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}
