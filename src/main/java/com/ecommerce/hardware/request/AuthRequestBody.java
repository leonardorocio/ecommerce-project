package com.ecommerce.hardware.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AuthRequestBody {

    @JsonProperty("accessToken")
    private String accessToken;
    @JsonProperty("tokenType")
    private String tokenType = "Bearer ";

    public AuthRequestBody(String accessToken) {
        this.accessToken = accessToken;
    }

    public AuthRequestBody(){};
}
