package com.ecommerce.hardware.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthRequestBody {

    private String accessToken;
    private String tokenType = "Bearer ";

    public AuthRequestBody(String accessToken) {
        this.accessToken = accessToken;
    }
}
