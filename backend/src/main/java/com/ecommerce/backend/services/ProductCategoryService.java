package com.ecommerce.backend.services;


import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.models.ProductCategory;
import com.ecommerce.backend.repository.ProductCategoryRepository;
import com.ecommerce.backend.repository.ProductRepository;
import com.ecommerce.backend.payload.ProductCategoryRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public List<ProductCategory> getProductCategories() {
        return productCategoryRepository.findAll();
    }

    public ProductCategory getCategoryById(Integer id) {
        return productCategoryRepository.findById(id).orElseThrow(
                () -> new BadRequestException("Categoria não existente"));
    }

    public List<Product> getCategoryProducts(Integer id) {
        ProductCategory productCategory = this.getCategoryById(id);
        return productCategory.getProductList();
    }

    public ProductCategory createProductCategory(ProductCategoryRequestBody productCategoryRequestBody) {
        ProductCategory newProductCategory = ProductCategory.builder()
                .name(productCategoryRequestBody.getName())
                .categoryImage(productCategoryRequestBody.getCategoryImage())
                .build();
        return productCategoryRepository.save(newProductCategory);
    }

    public ProductCategory updateProductCategory(ProductCategoryRequestBody productCategoryRequestBody, Integer id) {
        ProductCategory productCategory = getCategoryById(id);
        ProductCategory newCategory = ProductCategory.builder()
                .name(productCategoryRequestBody.getName())
                .categoryImage(productCategory.getCategoryImage())
                .build();
        newCategory.setId(productCategory.getId());
        return productCategoryRepository.save(newCategory);
    }

    public void deleteProductCategory(Integer id) {
        ProductCategory productCategory = getCategoryById(id);
        productCategoryRepository.delete(productCategory);
    }
}
