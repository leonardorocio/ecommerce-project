package com.ecommerce.hardware.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestBody {

    Integer user_id;
    Integer product_id;
}
