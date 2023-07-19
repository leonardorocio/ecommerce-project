package com.ecommerce.backend.controller;

import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.payload.OrderRequestBody;
import com.ecommerce.backend.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Tag(name = "Order", description = "Descreve as operações sobre pedidos")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    @Operation(summary = "Buscar todos os pedidos",
            description = "Retorna todos os pedidos do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a lista de pedidos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Orders>> getOrders() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar pedido",
            description = "Recebe um id de pedido e retorna o pedido associado ao id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o pedido"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Orders> getOrderById(@PathVariable int id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/open")
    @Operation(summary = "Buscar pedidos abertos",
            description = "Retorna todos os pedidos abertos no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de pedidos abertos"),
            @ApiResponse(responseCode = "404", description = "Todos os pedidos estão fechado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Orders>> getOpenOrders() {
        return ResponseEntity.ok(orderService.getOpenOrders());
    }

    @PostMapping
    @Operation(summary = "Criar um pedido",
            description = "Recebe um OrderRequestBody, mapeia para pedido e salva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o pedido salvo"),
            @ApiResponse(responseCode = "400", description = "Argumentos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Orders> postOrder(@RequestBody @Valid OrderRequestBody orderRequestBody) {
        return new ResponseEntity<>(orderService.postOrder(orderRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/close")
    @Operation(summary = "Fechar um pedido",
            description = "Recebe o id do pedido a ser fechado e o fecha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o pedido fechado"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Orders> closeOrder(@PathVariable int id) {
        return ResponseEntity.ok(orderService.closeOrder(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um pedido",
            description = "Recebe um id de pedido e deleta o pedido associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido apagado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Argumentos inválidos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
