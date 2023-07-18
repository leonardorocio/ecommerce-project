package com.ecommerce.backend.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteRequestBody {

    @NotNull
    @Schema(name = "productId", description = "The ID of the Product you are liking", example = "1", ref = "Product")
    private Integer productId;

    @NotNull
    @Schema(name = "userId", description = "The ID of the User liking the product", example = "1", ref = "User")
    private Integer userId;
}
