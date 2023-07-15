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
@Tag(name = "Category", description = "Descreve as operações de categoria de produto")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping
    @Operation(summary = "Buscar categorias",
            description = "Busca todas as categorias do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de categorias"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<ProductCategory>> getProductCategories() {
        return ResponseEntity.ok(productCategoryService.getProductCategories());
    }

    @PostMapping
    @Operation(summary = "Criar uma categoria",
            description = "Recebe um ProductCategoryRequestBody, mapeia para uma categoria e salva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna a categoria salva"),
            @ApiResponse(responseCode = "400", description = "Argumentos inválidos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<ProductCategory> postProductCategory(
            @RequestBody ProductCategoryRequestBody productCategoryRequestBody) {
        return new ResponseEntity<>(productCategoryService.createProductCategory(productCategoryRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma categoria",
            description = "Recebe o id de uma categoria e um ProductCategoryRequestBody, mapeia para a categoria do id e atualiza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a categoria atualizada"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "400", description = "Argumentos inválidos"),
            @ApiResponse(responseCode = "401", description = "Falha na autenticação")
    })
    public ResponseEntity<ProductCategory> updateProductCategory(
            @RequestBody @Valid ProductCategoryRequestBody productCategoryRequestBody, @PathVariable Integer id) {
        return ResponseEntity.ok(productCategoryService.updateProductCategory(productCategoryRequestBody, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma categoria",
            description = "Recebe o id da categoria a ser apagada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Void> deleteProductCategory(@PathVariable Integer id) {
        productCategoryService.deleteProductCategory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
