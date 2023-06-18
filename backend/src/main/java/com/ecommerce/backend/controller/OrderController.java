package com.ecommerce.backend.controller;

import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.payload.OrderRequestBody;
import com.ecommerce.backend.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path = "/order", produces = MediaType.APPLICATION_JSON_VALUE)
@RestController
@Tag(name = "Order", description = "Describes the order related operations")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping
    @Operation(summary = "Returns all the orders in the database",
            description = "Takes no parameters, returns all the orders in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of orders"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Orders>> getOrder() {
        return ResponseEntity.ok(orderService.getOrders());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a order based on id",
            description = "Takes an order's id and returns the order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the order"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Orders> getOrderById(@PathVariable int id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Returns all the orders from a user",
            description = "Takes an user's id and returns all of it's orders",
            tags = {"Order", "User"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of orders of the user"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Orders>> getUsersOrders(@PathVariable int id) {
        return ResponseEntity.ok(orderService.getOrdersByUser(id));
    }

    @GetMapping("/open")
    @Operation(summary = "Returns all the open orderss",
            description = "Takes no paramenter, returns all the open orders")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of open orders"),
            @ApiResponse(responseCode = "404", description = "Open orders not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<Orders>> getOpenOrders() {
        return ResponseEntity.ok(orderService.getOpenOrders());
    }

    @PostMapping
    @Operation(summary = "Creates an order",
            description = "Receives a OrderRequestBody, maps to a Order and saves in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns the order save in the database"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Orders> postOrder(@RequestBody @Valid OrderRequestBody orderRequestBody) {
        return new ResponseEntity<>(orderService.postOrder(orderRequestBody), HttpStatus.CREATED);
    }

    @PutMapping("/{id}/close")
    @Operation(summary = "Closes an order",
            description = "Takes an order's id and closes the order")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the order closed"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Orders> closeOrder(@PathVariable int id) {
        return ResponseEntity.ok(orderService.closeOrder(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an order",
            description = "Takes an order's id and deletes from the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Order successfully deleted"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Void> deleteOrder(@PathVariable int id) {
        orderService.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
