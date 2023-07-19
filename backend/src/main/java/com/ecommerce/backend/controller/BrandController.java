package com.ecommerce.backend.controller;

import com.ecommerce.backend.models.Brand;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.payload.BrandRequestBody;
import com.ecommerce.backend.services.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
@RequiredArgsConstructor
@Tag(name = "Brands", description = "Descreve as operações de marcas")
@SecurityRequirement(name = "Bearer Authentication")
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    @Operation(summary = "Buscar todos as marcas",
            description = "Retorna todos as marcas no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna as marcas"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Brand>> getBrands() {
        return ResponseEntity.ok(brandService.getBrands());
    }

    @GetMapping("/{id}/products")
    @Operation(summary = "Buscar todos os produtos de uma marca",
            description = "Recebe o id de uma marca e retorna todos os produtos dessa marca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os produtos da marcar"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Product>> getBrandProducts(@PathVariable int id) {
        return ResponseEntity.ok(brandService.getBrandProducts(id));
    }

    @PostMapping
    @Operation(summary = "Criar marca",
            description = "Recebe um BrandRequestBody, mapeia para uma marca e então salva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna a marca criada"),
            @ApiResponse(responseCode = "400", description = "Argumentos Inválidos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Brand> createBrand(@RequestBody @Valid BrandRequestBody brandRequestBody) {
        return new ResponseEntity<Brand>(brandService.createBrand(brandRequestBody),HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma marca",
            description = "Recebe um BrandRequestBody e um id, mapeia para a marca desse id e salva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a marca atualizada"),
            @ApiResponse(responseCode = "400", description = "Argumentos Inválidos"),
            @ApiResponse(responseCode = "404", description = "Marca não encontrada"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Brand> updateBrand(@RequestBody @Valid BrandRequestBody brandRequestBody,
                                             @PathVariable int id) {
        return ResponseEntity.ok(brandService.updateBrand(brandRequestBody, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma marca",
            description = "Recebe um id de marca e então deleta a marca")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Marca deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Marca não encontrada"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Void> deleteBrand(@PathVariable int id) {
        brandService.deleteBrand(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
