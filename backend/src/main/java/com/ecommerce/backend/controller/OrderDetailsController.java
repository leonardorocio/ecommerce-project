package com.ecommerce.backend.controller;

import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.payload.OrderDetailsRequestBody;
import com.ecommerce.backend.services.OrderDetailsService;
import java.util.List;

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

@RestController
@RequestMapping("/details")
@Tag(name = "Details", description = "Descreve as operações de detalhes de pedido")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class OrderDetailsController {

    private final OrderDetailsService orderDetailsService;

    @GetMapping
    @Operation(summary = "Buscar detalhes de pedido",
            description = "Retorna todos os detalhes de pedido presentes no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de detalhes de pedido"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<OrderDetails>> getOrdersDetails() {
        return ResponseEntity.ok(orderDetailsService.getOrdersDetails());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar detalhes de pedido",
            description = "Recebe um id de detalhes de pedido e retorna o detalhes de pedido associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o detalhes de pedido associado a aquele id"),
            @ApiResponse(responseCode = "404", description = "Detalhes de pedido não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<OrderDetails> getOrdersDetailsById(@PathVariable int id) {
        return ResponseEntity.ok(orderDetailsService.getOrderDetailsById(id));
    }

    @PostMapping
    @Operation(summary = "Criar detalhes de pedido",
            description = "Recebe um OrderDetailsRequestBody, mapeia para detalhes de pedido e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o detalhes de pedido criado"),
            @ApiResponse(responseCode = "400", description = "Argumentos inválidos"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<OrderDetails> postOrderDetails(@RequestBody @Valid OrderDetailsRequestBody orderDetailsRequestBody) {
        return new ResponseEntity<>(orderDetailsService.postOrderDetails(orderDetailsRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um detalhes de pedido",
            description = "Recebe um id de detalhes de pedido e deleta o detalhes de pedido associado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Detalhes de pedido deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Detalhes de pedido não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable int id) {
        orderDetailsService.deleteOrderDetails(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um detalhes de pedido",
            description = "Recebe um OrderDetailsRequestBody, mapeia para o detalhes de pedido do id recebido e atualiza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o detalhe de pedido atualizado"),
            @ApiResponse(responseCode = "404", description = "Detalhes de pedido não encontrado"),
            @ApiResponse(responseCode = "400", description = "Argumentos inválidos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<OrderDetails> updateOrderDetails(@Valid @RequestBody OrderDetailsRequestBody orderDetailsRequestBody,
                                                           @PathVariable int id) {
        return ResponseEntity.ok(orderDetailsService.updateOrderDetails(orderDetailsRequestBody, id));
    }
}
