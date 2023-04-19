package com.ecommerce.backend.payload;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestBody {

    @NotNull
    Integer user_id;

    @NotNull
    Integer product_id;
}
