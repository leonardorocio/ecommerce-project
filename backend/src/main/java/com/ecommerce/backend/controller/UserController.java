package com.ecommerce.backend.controller;

import com.ecommerce.backend.configuration.JWTGenerator;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.payload.*;
import com.ecommerce.backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Log4j2
@Tag(name = "Users", description = "Descreve as operações de usuário")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Buscar usuários",
            description = "Retorna todos os usuários do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de usuários"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    @Operation(summary = "Criar um usuário",
            description = "Recebe um UserPostRequestBody, mapeia para usuário e salva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o usuário criado"),
            @ApiResponse(responseCode = "400", description = "Argumentos Inválidos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<User> createUser(@Valid @RequestBody UserPostRequestBody userPostRequestBody) {
        return new ResponseEntity<>(userService.createUser(userPostRequestBody), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um usuário",
            description = "Recebe o id do usuário e retorna o usuário associado ao id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o usuário"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Atualiza um usuário",
            description = "Recebe o id do usuário e um UserPatchRequestBody, mapeia para o usuário e atualiza ele")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o usuário atualizado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Argumentos Inválidos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserPatchRequestBody userPatchRequestBody,
                                           @PathVariable Integer id) {
        return ResponseEntity.ok(userService.updateUser(userPatchRequestBody, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um usuário",
            description = "Recebe o id do usuário a ser deletado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/change_password/{id}")
    @Operation(summary = "Deletar um usuário",
            description = "Recebe o id do usuário e um PasswordRequestBody e então atualiza a senha do usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Argumentos Inválidos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<String> changeUserPassword(@RequestBody @Valid PasswordRequestBody passwordRequestBody,
                                                     @PathVariable Integer id) {
        userService.updateUserPassword(passwordRequestBody, id);
        return ResponseEntity.ok("User password changed successfully");
    }
}
