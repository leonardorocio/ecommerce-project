package com.ecommerce.backend.payload;

import lombok.Data;

@Data
public class RefreshTokenRequestBody {

    private String requestToken;
}
