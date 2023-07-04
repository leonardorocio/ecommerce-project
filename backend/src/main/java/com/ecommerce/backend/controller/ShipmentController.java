package com.ecommerce.backend.controller;

import com.ecommerce.backend.models.Shipment;
import com.ecommerce.backend.payload.ShipmentRequestBody;
import com.ecommerce.backend.services.ShipmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipments")
@Tag(name = "Shipments", description = "Descreve as operações de entrega")
@SecurityRequirement(name = "Bearer Authentication")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping
    @Operation(summary = "Buscar entregas",
            description = "Retorna todas as entregas no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de entregas"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Shipment>> getShipments() {
        return ResponseEntity.ok(shipmentService.getShipments());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma entrega",
            description = "Recebe o id de uma entrega e retorna a entrega associada ao id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a entrega"),
            @ApiResponse(responseCode = "404", description = "Entrega não encontrada"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Shipment> getShipmentById(@PathVariable int id) {
        return ResponseEntity.ok(shipmentService.getShipmentById(id));
    }

    @GetMapping("/open")
    @Operation(summary = "Buscar entregas em andamento",
            description = "Retorna todas as entregas em andamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de entregas em andamento"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Shipment>> getOnGoingShipments() {
        return ResponseEntity.ok(shipmentService.getOnGoingShipments());
    }


    @PostMapping
    @Operation(summary = "Criar uma entrega",
            description = "Recebe um ShipmentRequestBody, mapeia para entrega e salva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna a entrega criada"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "404", description = "Transportadora não encontrado"),
            @ApiResponse(responseCode = "400", description = "Argumentos inválidos"),
            @ApiResponse(responseCode = "401", description = "Falha na autenticação")
    })
    public ResponseEntity<Shipment> postShipment(@RequestBody @Valid ShipmentRequestBody shipmentRequestBody) {
        return new ResponseEntity<>(shipmentService.postShipment(shipmentRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma entrega",
            description = "Recebe o id da entrega e um ShipmentRequestBody, mapeia para a entrega e atualiza")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a entrega atualizada"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado"),
            @ApiResponse(responseCode = "404", description = "Transportadora não encontrado"),
            @ApiResponse(responseCode = "404", description = "Entrega não encontrada"),
            @ApiResponse(responseCode = "400", description = "Argumentos inválidos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Shipment> updateShipment(@Valid @RequestBody ShipmentRequestBody shipmentRequestBody,
                                                   @PathVariable int id) {
        return ResponseEntity.ok(shipmentService.updateShipment(shipmentRequestBody, id));
    }

    @PutMapping("/{id}/close")
    @Operation(summary = "Closes a Shipment in database",
            description = "Takes a shipment's id and closes the shipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the Shipment closed"),
            @ApiResponse(responseCode = "404", description = "Shipment not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Shipment> closeShipment(@PathVariable int id) {
        return ResponseEntity.ok(shipmentService.closeShipment(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma entrega",
                description = "Recebe o id da entrega a ser deletada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Entrega deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Entrega não encontrada"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Void> deleteShipment(@PathVariable int id) {
        shipmentService.deleteShipment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}/close")
    @Operation(summary = "Fechar entrega",
            description = "Recebe o id da entrega a ser fechada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a entrega fechada"),
            @ApiResponse(responseCode = "404", description = "Entrega não encontrada"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Shipment> closeShipment(@PathVariable int id) {
        return ResponseEntity.ok(shipmentService.closeShipment(id));
    }

}
