package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.OrderDetails;
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
            Orders mappedOrders = Orders.builder()
                    .orderedDate(LocalDate.now())
                    .closed(false)
                    .customer(userService.getUserById(orderRequestBody.getCustomerId()))
                    .build();
            return mappedOrders;
    }

}
