package com.ecommerce.backend.controller;

import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.payload.OrderDetailsRequestBody;
import com.ecommerce.backend.services.OrderDetailsService;
import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orderDetails")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping
    public ResponseEntity<List<OrderDetails>> getOrdersDetails() {
        return ResponseEntity.ok(orderDetailsService.getOrdersDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDetails> getOrdersDetailsById(@PathVariable int id) {
        return ResponseEntity.ok(orderDetailsService.getOrderDetailsById(id));
    }

    @PostMapping
    public ResponseEntity<OrderDetails> postOrderDetails(@RequestBody @Valid OrderDetailsRequestBody orderDetailsRequestBody) {
        return new ResponseEntity<>(orderDetailsService.postOrderDetails(orderDetailsRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrderDetails(@PathVariable int id) {
        orderDetailsService.deleteOrderDetails(id);
        return new ResponseEntity<String>("Order details deleted successfully", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDetails> updateOrderDetails(@Valid @RequestBody OrderDetailsRequestBody orderDetailsRequestBody,
                                                           @PathVariable int id) {
        return ResponseEntity.ok(orderDetailsService.updateOrderDetails(orderDetailsRequestBody, id));
    }
}
