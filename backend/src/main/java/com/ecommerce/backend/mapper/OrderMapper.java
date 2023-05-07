package com.ecommerce.backend.mapper;

import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.payload.OrderRequestBody;
import com.ecommerce.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class OrderMapper {

    @Autowired
    private UserService userService;

    public Orders mapToOrder(OrderRequestBody orderRequestBody) {
        // TODO Arrumar esse mapper
        Orders mappedOrders = Orders.builder()
                .orderedDate(LocalDate.now())
                .closed(false)
                .customer(userService.getUserById(orderRequestBody.getCustomerId()))
                .totalPrice(0.0)
                .shipment(null)
                .orderDetailsId(null)
                .build();
        return mappedOrders;
    }

    public Orders mapToOrderPatch(OrderRequestBody orderRequestBody) {
        Orders mappedOrders = Orders.builder()
                .closed(orderRequestBody.isClosed())
                .totalPrice(orderRequestBody.getTotalPrice())
//                .shipment(null)
//                .orderDetailsId(null)
                .build();
        return mappedOrders;
    }
}
