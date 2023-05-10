package com.ecommerce.backend.controller;


import com.ecommerce.backend.models.Shipment;
import com.ecommerce.backend.models.Shipper;
import com.ecommerce.backend.payload.ShipperRequestBody;
import com.ecommerce.backend.services.ShipperService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shipper")
public class ShipperController {

    @Autowired
    private ShipperService shipperService;

    @GetMapping
    public ResponseEntity<List<Shipper>> getShippers() {
        return ResponseEntity.ok(shipperService.getShippers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Shipper> getShipperById(@PathVariable int id) {
        return ResponseEntity.ok(shipperService.getShipperById(id));
    }

    @GetMapping("/{id}/open")
    public ResponseEntity<List<Shipment>> getShippersOnGoingShipments(@PathVariable int id) {
        return ResponseEntity.ok(shipperService.getShippersOnGoingShipments(id));
    }

    @PostMapping
    public ResponseEntity<Shipper> postShipper(@Valid @RequestBody ShipperRequestBody shipperRequestBody) {
        return ResponseEntity.ok(shipperService.postShipper(shipperRequestBody));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShipping(@PathVariable int id) {
        shipperService.deleteShipper(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Shipper> updateShipper(@Valid @RequestBody ShipperRequestBody shipperRequestBody,
                                                 @PathVariable int id) {
        return ResponseEntity.ok(shipperService.updateShipper(shipperRequestBody, id));
    }

}
