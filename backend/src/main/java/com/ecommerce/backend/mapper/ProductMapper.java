package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.payload.ProductPostRequestBody;
import com.ecommerce.backend.services.ProductCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {

    private final ProductCategoryService productCategoryService;

    public Product mapToProduct(ProductPostRequestBody productPostRequestBody) {
        if (validateProduct(productPostRequestBody)) {
            Product product = Product.builder()
                    .productImage(productPostRequestBody.getImageURL())
                    .price(productPostRequestBody.getPrice())
                    .shortDescription(productPostRequestBody.getShortDescription())
                    .detailedDescription(productPostRequestBody.getDetailedDescription())
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

    public boolean validateProduct(ProductPostRequestBody productPostRequestBody) {
        if (productPostRequestBody.getDiscount() <= 0.0 || productPostRequestBody.getDiscount() > 1.0) {
            throw new BadRequestException("Multiplicador de desconto não pode ser menor que 0 ou maior que 1");
        }
        if (productPostRequestBody.getStock() <= 0) {
            throw new BadRequestException("Não é possível criar produto com estoque vazio");
        }
        if (productPostRequestBody.getPrice() <= 0.0) {
            throw new BadRequestException("Não é possível criar produto de graça");
        }
        return true;
    }

}
