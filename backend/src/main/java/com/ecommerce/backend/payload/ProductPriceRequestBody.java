package com.ecommerce.backend.payload;

import lombok.Data;

@Data
public class ProductPriceRequestBody {

    double price;
    int stock;
    double discount;
}
