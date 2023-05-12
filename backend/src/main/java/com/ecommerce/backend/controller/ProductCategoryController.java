package com.ecommerce.backend.controller;


import com.ecommerce.backend.models.ProductCategory;
import com.ecommerce.backend.payload.ProductCategoryRequestBody;
import com.ecommerce.backend.services.ProductCategoryService;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@Tag(name = "ProductCategory", description = "Describes the ProductCategory related operations")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping
    @Operation(summary = "Returns all the productCategories in the database",
            description = "Takes no parameters, returns all the productCategories in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of productCategories"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<ProductCategory>> getProductCategories() {
        return ResponseEntity.ok(productCategoryService.getProductCategories());
    }

    @PostMapping
    @Operation(summary = "Creates a ProductCategory",
            description = "Takes a ProductCategoryRequestBody, returns the productCategory saved in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns the productCategory saved in the database"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<ProductCategory> postProductCategory(
            @RequestBody ProductCategoryRequestBody productCategoryRequestBody) {
        return new ResponseEntity<>(productCategoryService.createProductCategory(productCategoryRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a ProductCategory",
            description = "Takes a  productCategory's id and a ProductCategoryRequestBody, maps to a productCategory and updates in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the productCategory saved in the database"),
            @ApiResponse(responseCode = "404", description = "ProductCategory not found"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<ProductCategory> updateProductCategory(
            @RequestBody @Valid ProductCategoryRequestBody productCategoryRequestBody, @PathVariable Integer id) {
        return ResponseEntity.ok(productCategoryService.updateProductCategory(productCategoryRequestBody, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a productCategory from the database",
            description = "Takes a productCategory's id and then deletes it from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ProductCategory deleted successfully"),
            @ApiResponse(responseCode = "404", description = "ProductCategory not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Void> deleteProductCategory(
            @RequestBody ProductCategoryRequestBody productCategoryRequestBody, @PathVariable Integer id) {
        productCategoryService.deleteProductCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
