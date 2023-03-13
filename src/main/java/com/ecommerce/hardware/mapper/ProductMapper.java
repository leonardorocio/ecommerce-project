package com.ecommerce.hardware.mapper;

import com.ecommerce.hardware.exceptions.BadRequestException;
import com.ecommerce.hardware.models.Product;
import com.ecommerce.hardware.request.ProductPostRequestBody;
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
                .stock(1)
                .discount(1.0)
                .build();
        return product;
    }

    public boolean validateProduct(ProductPostRequestBody productPostRequestBody) {
        if (productPostRequestBody.getDiscount() <= 0.0 || productPostRequestBody.getDiscount() > 1.0) {
            throw new BadRequestException("Discount multiplier cannot be 0 or greater than 1");
        }
        if (productPostRequestBody.getStock() <= 0) {
            throw new BadRequestException("Cannot create new product with empty stock");
        }
        if (productPostRequestBody.getPrice() <= 0.0) {
            throw new BadRequestException("Cannot create product to be sold for free");
        }
        return true;
    }
}
