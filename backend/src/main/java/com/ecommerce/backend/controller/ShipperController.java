package com.ecommerce.backend.controller;


import com.ecommerce.backend.models.Shipment;
import com.ecommerce.backend.models.Shipper;
import com.ecommerce.backend.payload.ShipperRequestBody;
import com.ecommerce.backend.repository.ShipperRepository;
import com.ecommerce.backend.services.ShipperService;
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
@RequestMapping("/shipper")
@Tag(name = "Shipper", description = "Descreve as operações de Transportadora")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class ShipperController {

    private final ShipperService shipperService;

    @GetMapping
    @Operation(summary = "Buscar transportadoras",
            description = "Retorna todas as transportadoras no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de transportadoras"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Shipper>> getShippers() {
        return ResponseEntity.ok(shipperService.getShippers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar uma transportadora",
            description = "Recebe o id de uma transportadora e retorna a transportadora associada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a transportadora"),
            @ApiResponse(responseCode = "404", description = "Transportadora não encontrada"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Shipper> getShipperById(@PathVariable int id) {
        return ResponseEntity.ok(shipperService.getShipperById(id));
    }

    @GetMapping("/{id}/open")
    @Operation(summary = "Buscar entregas em andamento da transportadora",
            description = "Recebe o id de uma transportadora e retorna todas as suas entregas em andamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna uma lista de entregas em andamento"),
            @ApiResponse(responseCode = "404", description = "Transportadora não encontrada"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Shipment>> getShippersOnGoingShipments(@PathVariable int id) {
        return ResponseEntity.ok(shipperService.getShippersOnGoingShipments(id));
    }

    @PostMapping
    @Operation(summary = "Criar uma transportadora",
            description = "Recebe um ShipperRequestBody, mapeia para transportadora e salva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna a transportadora criada"),
            @ApiResponse(responseCode = "400", description = "Argumentos Inválidos"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Shipper> postShipper(@Valid @RequestBody ShipperRequestBody shipperRequestBody) {
        return new ResponseEntity<>(shipperService.postShipper(shipperRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar uma transportadora",
            description = "Recebe o id da transportadora a ser deletada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Transportadora deletada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Transportadora não encontrada"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Void> deleteShipping(@PathVariable int id) {
        shipperService.deleteShipper(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar uma transportadora",
            description = "Recebe o id da transportadora e um ShipperRequestBody, mapeia para a transportadora e atualiza ela")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna a transportadora atualizada"),
            @ApiResponse(responseCode = "404", description = "Transportadora não encontrada"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação"),
            @ApiResponse(responseCode = "400", description = "Argumentos inválidos")
    })
    public ResponseEntity<Shipper> updateShipper(@Valid @RequestBody ShipperRequestBody shipperRequestBody,
                                                 @PathVariable int id) {
        return ResponseEntity.ok(shipperService.updateShipper(shipperRequestBody, id));
    }

}
