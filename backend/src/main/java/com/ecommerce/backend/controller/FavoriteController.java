package com.ecommerce.backend.controller;

import com.ecommerce.backend.models.Favorite;
import com.ecommerce.backend.payload.FavoriteRequestBody;
import com.ecommerce.backend.services.FavoriteService;
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

@RequestMapping("/favorites")
@RestController
@RequiredArgsConstructor
@Tag(name = "Favorites", description = "Descreve as operações de produtos favoritados")
@SecurityRequirement(name = "Bearer Authentication")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    @Operation(summary = "Buscar todos os favoritos",
            description = "Retorna todos os favoritos no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os favoritos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Favorite>> getFavorites() {
        return ResponseEntity.ok(favoriteService.getFavorites());
    }

    @PostMapping
    @Operation(summary = "Criar favorito",
            description = "Recebe um FavoriteRequestBody e cria um novo favorito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o favorito criado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Favorite> createFavorite(@RequestBody @Valid FavoriteRequestBody favoriteRequestBody) {
        return new ResponseEntity<Favorite>(favoriteService.createFavorite(favoriteRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar favorito",
            description = "Recebe um id de favorito e deleta esse favorito")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Favorito deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Favorito não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Void> deleteFavorite(@PathVariable Integer id) {
        favoriteService.deleteFavorite(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
