package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.models.Shipment;
import com.ecommerce.backend.models.Shipper;
import com.ecommerce.backend.payload.ShipmentRequestBody;
import com.ecommerce.backend.services.OrderService;
import com.ecommerce.backend.services.ShipperService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ShipmentMapper {


    private final OrderService orderService;


    private final ShipperService shipperService;

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
            orderService.updateOrderTotalPrice(order.getId(), shipment.getShippingPrice());
            return shipment;
        }
        return null;
    }

    public boolean validateShipment(ShipmentRequestBody shipmentRequestBody, Orders orders) {
        if (orders.getShipment() != null) {
            throw new BadRequestException("Pedido já possui uma entrega relacionada");
        }

        if (shipmentRequestBody.getExpectedDeliveryDate().isBefore(LocalDate.now()) ||
            shipmentRequestBody.getExpectedDeliveryDate().isBefore(orders.getOrderedDate())) {
            throw new BadRequestException("Data de entrega esperada não pode ser antes de hoje ou da data de pedido inicial");
        }

        if (shipmentRequestBody.getShippingPrice() < 0) {
            throw new BadRequestException("Taxa de entrega não pode ser menor que 0");
        }
        return true;
    }
}
