package com.ecommerce.backend.handler;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.exceptions.BadRequestExceptionDetails;
import com.ecommerce.backend.exceptions.RefreshTokenException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException BRE) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Bad Request Exception. Check the docs")
                        .details(BRE.getMessage())
                        .developerMessage(BRE.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerAuthException(AuthenticationException authException) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .title("Authentication Error")
                        .details(authException.getMessage())
                        .developerMessage(authException.getClass().getName())
                        .build(), HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerNotValidArgException(MethodArgumentNotValidException MAE) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.BAD_REQUEST.value())
                        .title("Invalid argument for this action")
                        .details(MAE.getMessage())
                        .developerMessage(MAE.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerResourceNotFoundException(ResourceNotFoundException RNFE) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.NOT_FOUND.value())
                        .title("Resource not found.")
                        .details(RNFE.getMessage())
                        .developerMessage(RNFE.getClass().getName())
                        .build(), HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(RefreshTokenException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(RefreshTokenException RTE) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .title("Invalid Refresh Token")
                        .details(RTE.getMessage())
                        .developerMessage(RTE.getClass().getName())
                        .build(), HttpStatus.BAD_REQUEST
        );
    }
}
