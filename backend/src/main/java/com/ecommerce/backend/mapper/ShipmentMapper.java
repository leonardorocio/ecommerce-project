package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.models.Shipment;
import com.ecommerce.backend.payload.ShipmentRequestBody;
import com.ecommerce.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ShipmentMapper {

    @Autowired
    private OrderService orderService;

    public Shipment mapToShipment(ShipmentRequestBody shipmentRequestBody) {
        Orders order = orderService.getOrderById(shipmentRequestBody.getOrderId());
        if (validateShipment(shipmentRequestBody, order)) {
            Shipment shipment = Shipment.builder()
                    .delivered(shipmentRequestBody.isDelivered())
                    .expectedDeliveryDate(shipmentRequestBody.getExpectedDeliveryDate())
                    .shippingPrice(shipmentRequestBody.getShippingPrice())
                    .orders(order)
                    .build();
            return shipment;
        }
        return null;
    }

    public boolean validateShipment(ShipmentRequestBody shipmentRequestBody, Orders orders) {
        if (shipmentRequestBody.getExpectedDeliveryDate().isBefore(LocalDate.now()) ||
            shipmentRequestBody.getExpectedDeliveryDate().isBefore(orders.getOrderedDate())) {
            throw new BadRequestException("Expected delivery date cannot be before today or before ordering date");
        }
        if (shipmentRequestBody.getShippingPrice() < 0) {
            throw new BadRequestException("Shipping price cannot be lower than 0");
        }
        return true;
    }
}
