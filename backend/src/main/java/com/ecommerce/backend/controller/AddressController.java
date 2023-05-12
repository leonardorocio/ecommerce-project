package com.ecommerce.backend.controller;

import com.ecommerce.backend.models.Address;
import java.util.List;

import com.ecommerce.backend.payload.AddressRequestBody;
import com.ecommerce.backend.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/address")
@Tag(name = "Address", description = "Describes the address related operations")
@SecurityRequirement(name = "Bearer Authentication")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @GetMapping
    @Operation(summary = "Returns all the addresses in the database",
            description = "Returns all the addresses in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all the addresses"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Address>> getAddresses() {
        return ResponseEntity.ok(addressService.getAddresses());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns one address by it's ID",
            description = "The parameter 'id' is the id of the address you are searching")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns all the addresses"),
            @ApiResponse(responseCode = "404", description = "Address not found with this id"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Address> getAddressById(@PathVariable int id) {
        return ResponseEntity.ok(addressService.getAddressById(id));
    }

    @PostMapping
    @Operation(summary = "Creates a new address",
            description = "Receives an AddressRequestBody, maps it to an User and then saves in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the addresses saved in the database"),
            @ApiResponse(responseCode = "400", description = "Invalid arguments"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Address> createAddress(@Valid @RequestBody AddressRequestBody addressRequestBody) {
        return ResponseEntity.ok(addressService.createAddress(addressRequestBody));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an address from the database",
            description = "Receives an the id of the address you want to delete")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Address deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found with this id"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Void> deleteAddress(@PathVariable int id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an address from the database",
            description = "Receives an the id of the address you want to update and " +
                    "an AddressRequestBody to map the changes to the current Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Address updated successfully"),
            @ApiResponse(responseCode = "404", description = "Address not found with this id"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Address> updateAddress(@Valid @RequestBody AddressRequestBody addressRequestBody,
                                                 @PathVariable int id) {
        return ResponseEntity.ok(addressService.updateAddress(addressRequestBody, id));
    }
}
