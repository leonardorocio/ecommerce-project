package com.ecommerce.backend.handler;

import com.ecommerce.backend.exceptions.*;

import java.lang.reflect.Field;
import java.util.List;

import jakarta.validation.ConstraintViolationException;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatusCode statusCode, WebRequest request) {
            ExceptionDetails exceptionDetails = ExceptionDetails.builder()
                    .timestamp(LocalDateTime.now())
                    .status(statusCode.value())
                    .title(ex.getCause().getMessage())
                    .details(ex.getMessage())
                    .developerMessage(ex.getClass().getName())
                    .build();
            return new ResponseEntity<>(exceptionDetails, statusCode);
    }

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

    @ExceptionHandler({AuthenticationException.class, AuthenticationCredentialsNotFoundException.class})
    public ResponseEntity<BadRequestExceptionDetails> handlerAuthException(Exception authException) {
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

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(","));
        String fieldsMessage = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(","));
        return new ResponseEntity<>(
                ValidationDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .fields(fields)
                        .fieldsMessage(fieldsMessage)
                        .details(ex.getMessage())
                        .title("Invalid Fields")
                        .developerMessage(ex.getClass().getName())
                        .status(status.value())
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
    public ResponseEntity<BadRequestExceptionDetails> handlerRefreshTokenException(RefreshTokenException RTE) {
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .status(HttpStatus.UNAUTHORIZED.value())
                        .title("Invalid Refresh Token")
                        .details(RTE.getMessage())
                        .developerMessage(RTE.getClass().getName())
                        .build(), HttpStatus.UNAUTHORIZED
        );
    }

}
