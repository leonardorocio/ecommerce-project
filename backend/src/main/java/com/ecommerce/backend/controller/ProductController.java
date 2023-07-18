package com.ecommerce.backend.controller;


import com.ecommerce.backend.models.Comment;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.payload.ProductCategoryRequestBody;
import com.ecommerce.backend.payload.ProductPostRequestBody;
import com.ecommerce.backend.services.CommentService;
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
@Tag(name = "Products", description = "Descreve as operações de produto")
@SecurityRequirement(name = "Bearer Authentication")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    @Operation(summary = "Buscar produtos",
            description = "Retorna todos os produtos no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de produtos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Product>> getProducts() {
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping("/discounted")
    @Operation(summary = "Buscar produtos ordenando por desconto",
            description = "Retorna todos os produtos ordenados por desconto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de produtos ordenados por desconto"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Product>> getProductsSortedByDiscount() {
        return ResponseEntity.ok(productService.getProductsWithDiscount());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um produto",
            description = "Recebe um id de produto e retorna o produto associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o produto"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Product> getProductById(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    @Operation(summary = "Criar um produto",
            description = "Recebe um ProductPostRequestBody, mapeia para produto e salva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o produto criado"),
            @ApiResponse(responseCode = "400", description = "Argumentos Inválidos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Product> createProduct(@RequestBody @Valid ProductPostRequestBody productPostRequestBody) {
        return new ResponseEntity<>(productService.createProduct(productPostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Atualizar um produto",
            description = "Recebe o id do produto a atualizar e um ProductPostRequestBody, mapeia para o produto do id e atualiza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o produto atualizado"),
            @ApiResponse(responseCode = "400", description = "Argumentos inválidos"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Product> updateProduct(@RequestBody @Valid ProductPostRequestBody productPostRequestBody,
                                                 @PathVariable Integer id) {
        return ResponseEntity.ok(productService.updateProduct(productPostRequestBody, id));
    }

    @GetMapping(path = "/search")
    @Operation(summary = "Buscar produto por termo",
            description = "Recebe um termo e busca produtos que tenham o termo presente em categoria, descrição ou nome")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de produtos baseada no termo"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Product>> searchProduct(@RequestParam(defaultValue = "") String name) {
        return ResponseEntity.ok(productService.searchProduct(name));
    }

    @GetMapping(path = "/filter")
    @Operation(summary = "Filtrar por categoria",
            description = "Recebe um nome de categoria e filtra os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de produtos filtrados por categoria"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Product>> filterProductByCategory(
            @RequestBody ProductCategoryRequestBody productCategoryRequestBody) {
        return ResponseEntity.ok(productService.filterProductByCategory(productCategoryRequestBody));
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Deletar um produto",
            description = "Recebe um id do produto que deseja deletar e o deleta")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping(path = "/{id}/comments")
    @Operation(summary = "Buscar todos os comentários de um produto",
            description = "Recebe um id de produto e retorna todos os comentários desse produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os comentários do produto"),
            @ApiResponse(responseCode = "400", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Comment>> getCommentsByProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(commentService.getCommentsByProduct(id));
    }

}
