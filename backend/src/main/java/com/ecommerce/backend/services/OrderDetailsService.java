package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.OrderDetailsMapper;
import com.ecommerce.backend.mapper.OrderMapper;
import com.ecommerce.backend.mapper.PatchMapper;
import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.payload.OrderDetailsRequestBody;
import com.ecommerce.backend.repository.OrderDetailsRepository;
import java.util.List;

import jakarta.persistence.criteria.Order;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailsService {

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private PatchMapper patchMapper;
    @Autowired
    private OrderDetailsMapper orderDetailsMapper;

    @Autowired
    private OrderService orderService;
    public List<OrderDetails> getOrdersDetails() {
        return orderDetailsRepository.findAll();
    }

    public OrderDetails getOrderDetailsById(int id) {
        return orderDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No details available with this id"));
    }

    @Transactional(rollbackOn = Exception.class)
    public OrderDetails postOrderDetails(OrderDetailsRequestBody orderDetailsRequestBody) {
        OrderDetails orderDetails = orderDetailsMapper.mapToOrderDetails(orderDetailsRequestBody);
        int orderId = orderDetails.getOrders().getOrderId();
        int productId = orderDetails.getProduct().getProductId();
        if (orderDetailsRepository.existsOrderWithSameProduct(orderId, productId) != null) {
            throw new BadRequestException(
                    "Cannot create new order details for the same product and order. Please update the quantity");
        }
        return orderDetailsRepository.save(orderDetails);
    }

    public OrderDetails updateOrderDetails(OrderDetailsRequestBody orderDetailsRequestBody, int id) {
        OrderDetails originalOrderDetails = getOrderDetailsById(id);
        OrderDetails toUpdateOrderDetails = orderDetailsMapper.mapToPatchOrderDetails(orderDetailsRequestBody);
        patchMapper.mapToPatchRequest(originalOrderDetails, toUpdateOrderDetails);
        return orderDetailsRepository.save(originalOrderDetails);
    }

    public void deleteOrderDetails(int id) {
        OrderDetails orderDetailsToDelete = getOrderDetailsById(id);
        orderDetailsRepository.delete(orderDetailsToDelete);
    }
}
