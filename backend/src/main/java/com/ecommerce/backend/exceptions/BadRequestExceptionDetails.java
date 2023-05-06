package com.ecommerce.backend.exceptions;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class BadRequestExceptionDetails extends ExceptionDetails {

}
