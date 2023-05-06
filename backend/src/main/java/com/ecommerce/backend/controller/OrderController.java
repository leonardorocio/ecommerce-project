package com.ecommerce.backend.controller;

import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.payload.OrderRequestBody;
import com.ecommerce.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Orders>> getOrder() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable int id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @PostMapping
    public ResponseEntity<Orders> postOrder(@RequestBody OrderRequestBody orderRequestBody) {
        return ResponseEntity.ok(orderService.postOrder(orderRequestBody));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@RequestBody OrderRequestBody orderRequestBody,
                                              @PathVariable int id) {
        return ResponseEntity.ok(orderService.updateOrder(orderRequestBody, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<String>("Order deleted successfully", HttpStatus.NO_CONTENT);
    }
}
