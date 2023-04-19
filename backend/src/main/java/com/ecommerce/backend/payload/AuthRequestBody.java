package com.ecommerce.backend.payload;

import lombok.Data;

@Data
public class AuthRequestBody {

    private String accessToken;

    private String tokenType = "Bearer ";

    public AuthRequestBody(String accessToken) {
        this.accessToken = accessToken;
    }

    public AuthRequestBody(){};
}
