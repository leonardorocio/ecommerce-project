package com.ecommerce.backend.mapper;

import com.ecommerce.backend.models.Shipment;
import com.ecommerce.backend.payload.ShipmentRequestBody;
import com.ecommerce.backend.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipmentMapper {

    @Autowired
    private OrderService orderService;

    public Shipment mapToShipment(ShipmentRequestBody shipmentRequestBody) {
        Shipment shipment = Shipment.builder()
                .delivered(shipmentRequestBody.isDelivered())
                .expectedDeliveryDate(shipmentRequestBody.getExpectedDeliveryDate())
                .shippingPrice(shipmentRequestBody.getShippingPrice())
                .orders(orderService.getOrderById(shipmentRequestBody.getOrderId()))
                .build();
        return shipment;
    }
}
