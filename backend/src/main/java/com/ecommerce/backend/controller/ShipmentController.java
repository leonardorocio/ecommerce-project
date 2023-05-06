package com.ecommerce.backend.controller;

import com.ecommerce.backend.models.Shipment;
import com.ecommerce.backend.payload.ShipmentRequestBody;
import com.ecommerce.backend.services.ShipmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shipments")
public class ShipmentController {

    @Autowired
    private ShipmentService shipmentService;

    @GetMapping
    public ResponseEntity<List<Shipment>> getShipments() {
        return ResponseEntity.ok(shipmentService.getShipments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipment> getShipmentById(@PathVariable int id) {
        return ResponseEntity.ok(shipmentService.getShipmentById(id));
    }

    @PostMapping
    public ResponseEntity<Shipment> postShipment(@RequestBody @Valid ShipmentRequestBody shipmentRequestBody) {
        return new ResponseEntity<>(shipmentService.postShipment(shipmentRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShipment(@PathVariable int id) {
        shipmentService.deleteShipment(id);
        return new ResponseEntity<>("Shipment deleted succesfully", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipment> updateShipment(@Valid @RequestBody ShipmentRequestBody shipmentRequestBody,
                                                   @PathVariable int id) {
        return ResponseEntity.ok(shipmentService.updateShipment(shipmentRequestBody, id));
    }
}
