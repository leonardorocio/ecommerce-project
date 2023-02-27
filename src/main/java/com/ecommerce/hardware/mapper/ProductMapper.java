package com.ecommerce.hardware.mapper;

import com.ecommerce.hardware.models.Product;
import com.ecommerce.hardware.request.ProductPostRequestBody;
import com.ecommerce.hardware.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    public Product mapToProduct(ProductPostRequestBody productPostRequestBody) {
        Product product = Product.builder()
                .price(productPostRequestBody.getPrice())
                .description(productPostRequestBody.getDescription())
                .name(productPostRequestBody.getName())
                .stock(0)
                .discount(1.0)
                .build();
        return product;
    }
}
