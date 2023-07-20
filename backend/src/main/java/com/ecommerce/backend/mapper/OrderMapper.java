package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.payload.OrderRequestBody;
import com.ecommerce.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class OrderMapper {

    private final UserService userService;

    public Orders mapToOrder(OrderRequestBody orderRequestBody) {
        User customer = userService.getUserById(orderRequestBody.getCustomerId());
        Orders mappedOrders = Orders.builder()
                .orderDetailsList(Collections.emptyList())
                .orderedDate(LocalDate.now())
                .closed(false)
                .customer(customer)
                .build();
        return mappedOrders;
    }

    public boolean validateOrder(User customer) {
        if (customer.getUserOrders().stream().filter(orders -> !orders.isClosed()).count() > 1) {
            throw new BadRequestException("Usuário não pode ter mais de pedido em aberto");
        }
        return true;
    }
}
