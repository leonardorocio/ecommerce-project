package com.ecommerce.backend.controller;

import com.ecommerce.backend.models.Address;
import java.util.List;

import com.ecommerce.backend.payload.AddressRequestBody;
import com.ecommerce.backend.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/address")
@Tag(name = "Address", description = "Descreve as operações de endereço")
@SecurityRequirement(name = "Bearer Authentication")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    @Operation(summary = "Buscar todos os endereços",
            description = "Retorna todos os endereços do banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna todos os endereços"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<List<Address>> getAddresses() {
        return ResponseEntity.ok(addressService.getAddresses());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar um endereço",
            description = "Recebe um id de endereço e retorna o endereço associado ao id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna o endereço associado ao id"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Address> getAddressById(@PathVariable int id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @PostMapping
    @Operation(summary = "Criar um novo endereço",
            description = "Recebe um AddressRequestBody, mapeia para um endereço e salva no banco de dados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Retorna o endereço criado"),
            @ApiResponse(responseCode = "400", description = "Argumentos inválidos"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Address> createAddress(@Valid @RequestBody AddressRequestBody addressRequestBody) {
        return new ResponseEntity<>(addressService.createAddress(addressRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar um endereço",
            description = "Recebe o id do endereço que deseja deletar")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Void> deleteAddress(@PathVariable int id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar um endereço",
            description = "Recebe um id e um AddressRequestBody, mapeia para o endereço daquele id e atualiza o endereço ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado"),
            @ApiResponse(responseCode = "401", description = "Falha de autenticação")
    })
    public ResponseEntity<Address> updateAddress(@Valid @RequestBody AddressRequestBody addressRequestBody,
                                                 @PathVariable int id) {
        return ResponseEntity.ok(addressService.updateAddress(addressRequestBody, id));
    }
}
