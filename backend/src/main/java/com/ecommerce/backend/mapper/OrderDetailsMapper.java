package com.ecommerce.backend.mapper;

import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.payload.OrderDetailsRequestBody;
import com.ecommerce.backend.services.OrderService;
import com.ecommerce.backend.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailsMapper {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    public OrderDetails mapToOrderDetails(OrderDetailsRequestBody orderDetailsRequestBody) {
        OrderDetails orderDetails = OrderDetails.builder()
                .orders(orderService.getOrderById(orderDetailsRequestBody.getOrderId()))
                .product(productService.getProductById(orderDetailsRequestBody.getProductId()))
                .quantity(orderDetailsRequestBody.getQuantity())
                .build();
        return orderDetails;
    }

    public OrderDetails mapToPatchOrderDetails(OrderDetailsRequestBody orderDetailsRequestBody) {
        OrderDetails orderDetails = OrderDetails.builder()
                .quantity(orderDetailsRequestBody.getQuantity())
                .build();
        return orderDetails;
    }
}
