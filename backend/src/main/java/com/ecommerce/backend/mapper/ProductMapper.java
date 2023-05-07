package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.payload.ProductPostRequestBody;
import com.ecommerce.backend.payload.ProductPriceRequestBody;
import com.ecommerce.backend.services.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    @Autowired
    private ProductCategoryService productCategoryService;

    public Product mapToProduct(ProductPostRequestBody productPostRequestBody) {
        if (validateProduct(productPostRequestBody)) {
            Product product = Product.builder()
                    .price(productPostRequestBody.getPrice())
                    .description(productPostRequestBody.getDescription())
                    .name(productPostRequestBody.getName())
                    .stock(productPostRequestBody.getStock())
                    .discount(productPostRequestBody.getDiscount())
                    .productCategory(
                            productCategoryService.getCategoryById(productPostRequestBody.getCategory_id())
                    )
                    .build();
            return product;
        }
        return null;
    }

    public Product mapToProduct(ProductPriceRequestBody productPriceRequestBody) {
        if (validateProduct(productPriceRequestBody)) {
            Product product = Product.builder()
                    .price(productPriceRequestBody.getPrice())
                    .stock(productPriceRequestBody.getStock())
                    .discount(productPriceRequestBody.getDiscount())
                    .build();
            return product;
        }
        return null;
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

    public boolean validateProduct(ProductPriceRequestBody productPriceRequestBody) {
        if (productPriceRequestBody.getDiscount() <= 0.0 || productPriceRequestBody.getDiscount() > 1.0) {
            throw new BadRequestException("Discount multiplier cannot be 0 or greater than 1");
        }
        if (productPriceRequestBody.getStock() <= 0) {
            throw new BadRequestException("Cannot create new product with empty stock");
        }
        if (productPriceRequestBody.getPrice() <= 0.0) {
            throw new BadRequestException("Cannot create product to be sold for free");
        }
        return true;
    }
}
