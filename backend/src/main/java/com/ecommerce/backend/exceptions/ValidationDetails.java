package com.ecommerce.backend.exceptions;

import lombok.Getter;
import lombok.experimental.SuperBuilder;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@SuperBuilder
public class ValidationDetails extends ExceptionDetails{

    private final String fields;
    private final String fieldsMessage;
}
