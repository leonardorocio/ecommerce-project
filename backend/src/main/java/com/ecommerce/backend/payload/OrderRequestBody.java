package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestBody {

    @Schema(name = "Closed", description = "Specifies if the order is closed or not. If you're using a POST request for the order, it will always start with closed = false", example = "false")
    private boolean closed;

    @NotNull
    @Schema(name = "CustomerId", description = "The ID of the User related to the Order", example = "1")
    private int customerId;
}
