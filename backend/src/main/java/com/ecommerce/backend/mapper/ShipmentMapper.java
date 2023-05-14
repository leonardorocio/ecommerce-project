package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.models.Shipment;
import com.ecommerce.backend.models.Shipper;
import com.ecommerce.backend.payload.ShipmentRequestBody;
import com.ecommerce.backend.services.OrderService;
import com.ecommerce.backend.services.ShipperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class ShipmentMapper {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShipperService shipperService;

    public Shipment mapToShipment(ShipmentRequestBody shipmentRequestBody) {
        Orders order = orderService.getOrderById(shipmentRequestBody.getOrderId());
        Shipper shipper = shipperService.getShipperById(shipmentRequestBody.getShipperId());
        if (validateShipment(shipmentRequestBody, order)) {
            Shipment shipment = Shipment.builder()
                    .delivered(false)
                    .expectedDeliveryDate(shipmentRequestBody.getExpectedDeliveryDate())
                    .shippingPrice(shipmentRequestBody.getShippingPrice() + shipper.getFixedTax())
                    .orders(order)
                    .shipper(shipper)
                    .build();
            orderService.updateOrderTotalPrice(order.getOrderId(), shipment.getShippingPrice());
            return shipment;
        }
        return null;
    }

    public boolean validateShipment(ShipmentRequestBody shipmentRequestBody, Orders orders) {
        if (orders.getShipment() != null) {
            throw new BadRequestException("Order already has a designated shipment");
        }

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
