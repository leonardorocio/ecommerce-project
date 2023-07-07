package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "orderId", description = "The ID of the Order related to the OrderDetails", example = "1", ref = "Orders")
    private int orderId;

    @NotNull
    @Schema(name = "productId", description = "The ID of the Product related to the OrderDetails", example = "1", ref = "Product")
    private int productId;

    @NotNull
    @Schema(name = "quantity", description = "The quantity of the product that you want", example = "5")
    private int quantity;

}
