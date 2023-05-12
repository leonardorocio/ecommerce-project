package com.ecommerce.backend.controller;

import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.payload.OrderDetailsRequestBody;
import com.ecommerce.backend.services.OrderDetailsService;
import java.util.List;

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
@RequestMapping("/orderDetails")
@Tag(name = "OrderDetails", description = "Describes the OrderDetails related operations")
@SecurityRequirement(name = "Bearer Authentication")
public class OrderDetailsController {

    @Autowired
    private OrderDetailsService orderDetailsService;

    @GetMapping
    @Operation(summary = "Returns all the ordersDetails in the database",
            description = "Takes no parameters, returns all the ordersDetails in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of ordersDetails"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<OrderDetails>> getOrdersDetails() {
        return ResponseEntity.ok(orderDetailsService.getOrdersDetails());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns an orderDetails based on id",
            description = "Takes an orderDetails's id, returns the orderDetails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of orders"),
            @ApiResponse(responseCode = "404", description = "OrderDetails not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<OrderDetails> getOrdersDetailsById(@PathVariable int id) {
        return ResponseEntity.ok(orderDetailsService.getOrderDetailsById(id));
    }

    @PostMapping
    @Operation(summary = "Creates an OrderDetails",
            description = "Takes an OrderDetailsRequestBody, maps to an OrderDetails and saves in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the orderDetails saved in the database"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "404", description = "Order not found"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<OrderDetails> postOrderDetails(@RequestBody @Valid OrderDetailsRequestBody orderDetailsRequestBody) {
        return new ResponseEntity<>(orderDetailsService.postOrderDetails(orderDetailsRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes an orderDetails based on id",
            description = "Takes an orderDetails's id, deletes the orderDetails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "OrderDetails deleted successfully"),
            @ApiResponse(responseCode = "404", description = "OrderDetails not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Void> deleteOrderDetails(@PathVariable int id) {
        orderDetailsService.deleteOrderDetails(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an orderDetails based on id",
            description = "Takes an orderDetails's id and an OrderDetailsRequestBody, maps to a OrderDetails and updates the orderDetails")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Returns the orderDetails updated"),
            @ApiResponse(responseCode = "404", description = "OrderDetails not found"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<OrderDetails> updateOrderDetails(@Valid @RequestBody OrderDetailsRequestBody orderDetailsRequestBody,
                                                           @PathVariable int id) {
        return ResponseEntity.ok(orderDetailsService.updateOrderDetails(orderDetailsRequestBody, id));
    }
}
