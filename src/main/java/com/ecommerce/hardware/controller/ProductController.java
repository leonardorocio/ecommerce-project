package com.ecommerce.hardware.controller;


import com.ecommerce.hardware.models.Product;
import com.ecommerce.hardware.request.ProductPostRequestBody;
import com.ecommerce.hardware.services.ProductService;
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
    public ResponseEntity<Product> createProduct(@RequestBody ProductPostRequestBody productPostRequestBody) {
        return new ResponseEntity<>(productService.createProduct(productPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody ProductPostRequestBody productPostRequestBody) {
        return ResponseEntity.ok(productService.updateProduct(productPostRequestBody));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<String>("Produto deletado com sucesso", HttpStatus.NO_CONTENT);
    }

}
