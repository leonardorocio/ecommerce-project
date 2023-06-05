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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipper")
@Tag(name = "Shipper", description = "Describes the Shipper related operations")
@SecurityRequirement(name = "Bearer Authentication")
public class ShipperController {

    @Autowired
    private ShipperService shipperService;
    @Autowired
    private ShipperRepository shipperRepository;

    @GetMapping
    @Operation(summary = "Returns all the Shippers in the database",
            description = "Takes no parameters, returns all the Shippers in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of Shippers"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Shipper>> getShippers() {
        return ResponseEntity.ok(shipperService.getShippers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a Shipper based on id",
            description = "Takes a shipper's id and then returns the shipper")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns a shipper based on id"),
            @ApiResponse(responseCode = "404", description = "Shipper not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Shipper> getShipperById(@PathVariable int id) {
        return ResponseEntity.ok(shipperService.getShipperById(id));
    }

    @GetMapping("/{id}/open")
    @Operation(summary = "Returns all the on going Shipments of a Shipper",
            description = "Takes a shipper's id, returns all of it's on going Shipments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of on going Shipments"),
            @ApiResponse(responseCode = "404", description = "Shipper not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Shipment>> getShippersOnGoingShipments(@PathVariable int id) {
        return ResponseEntity.ok(shipperService.getShippersOnGoingShipments(id));
    }

    @PostMapping
    @Operation(summary = "Creates a new Shipper",
            description = "Takes a ShipperRequestBody, maps to Shipper and then saves in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns the list of Shipments"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Shipper> postShipper(@Valid @RequestBody ShipperRequestBody shipperRequestBody) {
        return new ResponseEntity<>(shipperService.postShipper(shipperRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Shipper",
            description = "Takes a shipper's id and then deletes it from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Shipper delete successfully"),
            @ApiResponse(responseCode = "404", description = "Shipper not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Void> deleteShipping(@PathVariable int id) {
        shipperService.deleteShipper(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a Shipper",
            description = "Takes a product's id and a ShipperRequestBody, maps to Shipper and saves in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of productCategories"),
            @ApiResponse(responseCode = "404", description = "Shipper not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed"),
            @ApiResponse(responseCode = "400", description = "Invalid arguments")
    })
    public ResponseEntity<Shipper> updateShipper(@Valid @RequestBody ShipperRequestBody shipperRequestBody,
                                                 @PathVariable int id) {
        return ResponseEntity.ok(shipperService.updateShipper(shipperRequestBody, id));
    }

}
