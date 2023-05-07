package com.ecommerce.backend.controller;


import com.ecommerce.backend.models.ProductCategory;
import com.ecommerce.backend.payload.ProductCategoryRequestBody;
import com.ecommerce.backend.services.ProductCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class ProductCategoryController {

    private final ProductCategoryService productCategoryService;

    @GetMapping
    public ResponseEntity<List<ProductCategory>> getProductCategories() {
        return ResponseEntity.ok(productCategoryService.getProductCategories());
    }

    @PostMapping
    public ResponseEntity<ProductCategory> postProductCategory(
            @RequestBody ProductCategoryRequestBody productCategoryRequestBody) {
        return new ResponseEntity<>(productCategoryService.createProductCategory(productCategoryRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductCategory> updateProductCategory(
            @RequestBody @Valid ProductCategoryRequestBody productCategoryRequestBody, @PathVariable Integer id) {
        return ResponseEntity.ok(productCategoryService.updateProductCategory(productCategoryRequestBody, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductCategory(
            @RequestBody ProductCategoryRequestBody productCategoryRequestBody, @PathVariable Integer id) {
        productCategoryService.deleteProductCategory(id);
        return new ResponseEntity<>("Categoria foi deletada com sucesso", HttpStatus.NO_CONTENT);
    }
}
