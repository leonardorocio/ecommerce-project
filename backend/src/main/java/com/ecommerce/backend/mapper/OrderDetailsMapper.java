package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.payload.OrderDetailsRequestBody;
import com.ecommerce.backend.repository.OrderDetailsRepository;
import com.ecommerce.backend.services.OrderDetailsService;
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

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    public OrderDetails mapToOrderDetails(OrderDetailsRequestBody orderDetailsRequestBody) {
        if (validateOrderDetails(orderDetailsRequestBody)) {
            Product product = productService.getProductById(orderDetailsRequestBody.getProductId());
            OrderDetails orderDetails = OrderDetails.builder()
                    .orders(orderService.getOrderById(orderDetailsRequestBody.getOrderId()))
                    .product(product)
                    .quantity(orderDetailsRequestBody.getQuantity())
                    .build();
            orderService.updateOrderTotalPrice(orderDetailsRequestBody.getOrderId(), product.getPrice() * orderDetails.getQuantity());
            return orderDetails;
        }
        return null;
    }

    public OrderDetails mapToPatchOrderDetails(OrderDetailsRequestBody orderDetailsRequestBody) {
        OrderDetails orderDetails = OrderDetails.builder()
                .quantity(orderDetailsRequestBody.getQuantity())
                .build();
        return orderDetails;
    }

    public boolean validateOrderDetails(OrderDetailsRequestBody orderDetailsRequestBody) {
        if (orderDetailsRequestBody.getQuantity() < 1) {
            throw new BadRequestException("Order details product quantity cannot be lower than 1.");
        }
        int orderId = orderDetailsRequestBody.getOrderId();
        int productId = orderDetailsRequestBody.getProductId();
        if (orderDetailsRepository.existsOrderWithSameProduct(orderId, productId) != null) {
            throw new BadRequestException(
                    "Cannot create new order details for the same product and order. Please update the quantity"
            );
        }
        return true;
    }
}
