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
@Tag(name = "Shipment", description = "Describes the Shipment related operations")
@SecurityRequirement(name = "Bearer Authentication")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping
    @Operation(summary = "Returns all the Shipments in the database",
            description = "Takes no parameters, returns all the Shipments in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of Shipments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Shipment>> getShipments() {
        return ResponseEntity.ok(shipmentService.getShipments());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a Shipment based on id",
            description = "Takes a shipment's id, returns the shipment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of Shipments"),
            @ApiResponse(responseCode = "404", description = "Shipment not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Shipment> getShipmentById(@PathVariable int id) {
        return ResponseEntity.ok(shipmentService.getShipmentById(id));
    }

    @GetMapping("/open")
    @Operation(summary = "Returns all the on going Shipments in the database",
            description = "Takes no parameters, returns all the on going Shipments in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of on going Shipments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Shipment>> getOnGoingShipments() {
        return ResponseEntity.ok(shipmentService.getOnGoingShipments());
    }


    @PostMapping
    @Operation(summary = "Creates a new Shipment in database",
            description = "Takes a ShipmentRequestBody, maps to Shipment and saves in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns the Shipment saved in the database"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "404", description = "Shipper not found"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Shipment> postShipment(@RequestBody @Valid ShipmentRequestBody shipmentRequestBody) {
        return new ResponseEntity<>(shipmentService.postShipment(shipmentRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a Shipment in database",
            description = "Takes a shipment's id and a ShipmentRequestBody, maps to Shipment and saves in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the Shipment saved in the database"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "404", description = "Shipper not found"),
            @ApiResponse(responseCode = "404", description = "Shipment not found"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Shipment> updateShipment(@Valid @RequestBody ShipmentRequestBody shipmentRequestBody,
                                                   @PathVariable int id) {
        return ResponseEntity.ok(shipmentService.updateShipment(shipmentRequestBody, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Shipment",
                description = "Takes a shipment's id and then deletes it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Shipment deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Shipment not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Void> deleteShipment(@PathVariable int id) {
        shipmentService.deleteShipment(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
