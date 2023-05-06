package com.ecommerce.backend.controller;


import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.payload.ProductCategoryRequestBody;
import com.ecommerce.backend.payload.ProductPostRequestBody;
import com.ecommerce.backend.payload.ProductPriceRequestBody;
import com.ecommerce.backend.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/discounted")
    public ResponseEntity<List<Product>> getProductsSortedByDiscount() {
        return ResponseEntity.ok(productService.getProductsWithDiscount());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductPostRequestBody productPostRequestBody) {
        return new ResponseEntity<>(productService.createProduct(productPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductPostRequestBody productPostRequestBody,
                                                 @PathVariable Integer id) {
        return ResponseEntity.ok(productService.updateProduct(productPostRequestBody, id));
    }

    @GetMapping(path = "/search")
    public ResponseEntity<List<Product>> searchProduct(@RequestParam(defaultValue = "") String name) {
        return ResponseEntity.ok(productService.searchProduct(name));
    }

    @GetMapping(path = "/filter")
    public ResponseEntity<List<Product>> filterProductByCategory(
            @RequestBody ProductCategoryRequestBody productCategoryRequestBody) {
        return ResponseEntity.ok(productService.filterProductByCategory(productCategoryRequestBody));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<String>("Produto deletado com sucesso", HttpStatus.NO_CONTENT);
    }

    @PatchMapping(path = "/{id}")
    public ResponseEntity<Product> setProductPrice(@RequestBody @Valid ProductPriceRequestBody productPriceRequestBody,
                                                   @PathVariable Integer id) {
        return ResponseEntity.ok(productService.setProductPrice(productPriceRequestBody, id));
    }

}
