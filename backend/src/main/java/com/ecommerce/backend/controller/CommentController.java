package com.ecommerce.backend.controller;


import com.ecommerce.backend.models.Comment;
import com.ecommerce.backend.payload.CommentPostRequestBody;
import com.ecommerce.backend.services.CommentService;
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
@RequestMapping(path = "/comments")
@Tag(name = "Comments", description = "Descreve as operações de comentário")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    @Operation(summary = "Buscar todos os comentários",
            description = "Retorna todos os comentários no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna os comentários"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Comment>> getComments() {
        return ResponseEntity.ok(commentService.getComments());
    }

    @PostMapping
    @Operation(summary = "Criar um comentário",
            description = "Recebe um CommentPostRequestBody, mapeia para um comentário e salva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o comentário criado"),
            @ApiResponse(responseCode = "400", description = "Argumentos Inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Comment> postComment(@RequestBody @Valid CommentPostRequestBody commentPostRequestBody) {
        return new ResponseEntity<>(commentService.createComment(commentPostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Deletar um comentário",
            description = "Recebe um id de comentário e apaga o comentário associado ao id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comentário apagado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Comentário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Void> deleteComment(@PathVariable Integer id) {
        commentService.deleteComment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um comentário",
            description = "Recebe o id do comentário que será atualizado e um CommentPostRequestBody com as mudanças")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns updated comment"),
            @ApiResponse(responseCode = "404", description = "Comment not found"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Comment> updateComment(@RequestBody @Valid CommentPostRequestBody commentPostRequestBody,
                                                 @PathVariable Integer id) {
        return ResponseEntity.ok(commentService.updateComment(commentPostRequestBody, id));
    }
}
