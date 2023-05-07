package com.ecommerce.backend.payload;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsRequestBody {

    @NotNull
    private int orderId;

    @NotNull
    private int productId;

    @NotNull
    private int quantity;

}
