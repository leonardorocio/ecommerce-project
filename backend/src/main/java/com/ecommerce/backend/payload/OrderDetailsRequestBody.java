package com.ecommerce.backend.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderDetailsRequestBody {

    @NotNull
    private int orderId;

    @NotNull
    private int productId;

    @NotEmpty
    private int quantity;

}
