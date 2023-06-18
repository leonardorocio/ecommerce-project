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
        Product product = productService.getProductById(orderDetailsRequestBody.getProductId());
        if (validateOrderDetails(orderDetailsRequestBody, product)) {
            OrderDetails orderDetails = OrderDetails.builder()
                    .orders(orderService.getOrderById(orderDetailsRequestBody.getOrderId()))
                    .product(product)
                    .quantity(orderDetailsRequestBody.getQuantity())
                    .build();
            orderService.updateOrderTotalPrice(orderDetailsRequestBody.getOrderId(), product.getPrice() * product.getDiscount() * orderDetails.getQuantity());
            return orderDetails;
        }
        return null;
    }

    public OrderDetails mapToUpdateOrderDetails(OrderDetailsRequestBody orderDetailsRequestBody) {
        Product product = productService.getProductById(orderDetailsRequestBody.getProductId());
        if (validateUpdateOrderDetails(orderDetailsRequestBody, product)) {
            OrderDetails orderDetails = OrderDetails.builder()
                    .orders(orderService.getOrderById(orderDetailsRequestBody.getOrderId()))
                    .product(product)
                    .quantity(orderDetailsRequestBody.getQuantity())
                    .build();
            return orderDetails;
        }
        return null;
    }

    public boolean validateOrderDetails(OrderDetailsRequestBody orderDetailsRequestBody, Product product) {
        if (orderDetailsRequestBody.getQuantity() < 1) {
            throw new BadRequestException("Quantidade do item não pode ser menor que 1.");
        }
        if (orderDetailsRequestBody.getQuantity() > product.getStock()) {
            throw new BadRequestException("Quantitidade pedida não pode ser maior que estoque disponível");
        }
        int orderId = orderDetailsRequestBody.getOrderId();
        int productId = orderDetailsRequestBody.getProductId();
        if (orderDetailsRepository.existsOrderWithSameProduct(orderId, productId)) {
            throw new BadRequestException(
                    "Não é possível criar novo item para o mesmo pedido e mesmo produto. Atualize a quantidade"
            );
        }
        return true;
    }

    public boolean validateUpdateOrderDetails(OrderDetailsRequestBody orderDetailsRequestBody, Product product) {
        if (orderDetailsRequestBody.getQuantity() < 1) {
            throw new BadRequestException("Quantidade do item não pode ser menor que 1.");
        }
        if (orderDetailsRequestBody.getQuantity() > product.getStock()) {
            throw new BadRequestException("Quantitidade pedida não pode ser maior que estoque disponível");
        }
        return true;
    }
}
