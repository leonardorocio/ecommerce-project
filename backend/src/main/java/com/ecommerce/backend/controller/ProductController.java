package com.ecommerce.backend.controller;


import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.payload.ProductCategoryRequestBody;
import com.ecommerce.backend.payload.ProductPostRequestBody;
import com.ecommerce.backend.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "Describes the Product related operations")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    @Operation(summary = "Returns all the Products in the database",
            description = "Takes no parameters, returns all the Products in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of Products"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/discounted")
    @Operation(summary = "Returns all the Products in the database, sorted by discount",
            description = "Takes no parameters, returns all the Products in the database, sorted from the discount multiplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of Products"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Product>> getProductsSortedByDiscount() {
        return ResponseEntity.ok(productService.getProductsWithDiscount());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a product based on id",
            description = "Takes the product's id, returns the product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the product related to the id"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    @Operation(summary = "Create a new Product",
            description = "Takes a ProductPostRequestBody, maps to Product and saves in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns the Product saved in the database"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductPostRequestBody productPostRequestBody) {
        return new ResponseEntity<>(productService.createProduct(productPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Updates a Product",
            description = "Takes a product's id and a ProductPostRequestBody, maps to Product and saves in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the Product saved in the database"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "404", description = "Product Id not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductPostRequestBody productPostRequestBody,
                                                 @PathVariable Integer id) {
        return ResponseEntity.ok(productService.updateProduct(productPostRequestBody, id));
    }

    @GetMapping(path = "/search")
    @Operation(summary = "Search for Products",
            description = "Takes a request parameter and searches for Product based on ProductCategory, Product's name and description")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of products based on the search term"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Product>> searchProduct(@RequestParam(defaultValue = "") String name) {
        return ResponseEntity.ok(productService.searchProduct(name));
    }

    @GetMapping(path = "/filter")
    @Operation(summary = "Search for a Product",
            description = "Takes a request parameter and searches for Product based on ProductCategory")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of products based on productCategory"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Product>> filterProductByCategory(
            @RequestBody ProductCategoryRequestBody productCategoryRequestBody) {
        return ResponseEntity.ok(productService.filterProductByCategory(productCategoryRequestBody));
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Deletes a Product",
            description = "Takes a product's id and then deletes from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Product deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
