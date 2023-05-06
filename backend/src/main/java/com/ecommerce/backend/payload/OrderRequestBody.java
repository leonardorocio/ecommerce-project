package com.ecommerce.backend.payload;

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

    @NotEmpty
    private double totalPrice;

    private boolean closed;

    @NotEmpty
    private int customerId;

    private int shipmentId;
}
