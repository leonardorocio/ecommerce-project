package com.ecommerce.backend.mapper;

import com.ecommerce.backend.models.Brand;
import com.ecommerce.backend.payload.BrandRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BrandMapper {

    public Brand mapToBrand(BrandRequestBody brandRequestBody) {
        Brand brand = Brand.builder()
                .name(brandRequestBody.getName())
                .brandLogoImage(brandRequestBody.getBrandLogoImage())
                .build();
        return brand;
    }
}
