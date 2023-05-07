package com.ecommerce.backend.exceptions;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class RefreshTokenException extends RuntimeException {

    public RefreshTokenException(String refresh_token_invalid) {
        super(refresh_token_invalid);
    }
}
