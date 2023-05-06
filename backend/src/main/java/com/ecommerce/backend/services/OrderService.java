package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.OrderMapper;
import com.ecommerce.backend.mapper.PatchMapper;
import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.payload.OrderRequestBody;
import com.ecommerce.backend.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private PatchMapper patchMapper;

    public List<Orders> getOrders() {
        return orderRepository.findAll();
    }

    public Orders getOrderById(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order not found"));
    }

    public Orders postOrder(OrderRequestBody orderRequestBody) {
        Orders orders = orderMapper.mapToOrder(orderRequestBody);
        return orderRepository.save(orders);
    }

    public Orders updateOrder(OrderRequestBody orderRequestBody, int id) {
        Orders originalOrders = getOrderById(id);
        Orders updatedOrders = orderMapper.mapToOrderPatch(orderRequestBody);
        patchMapper.mapToPatchRequest(originalOrders, updatedOrders);
        return orderRepository.save(originalOrders);
    }

    public void deleteOrder(int id) {
        Orders ordersToDelete = getOrderById(id);
        orderRepository.delete(ordersToDelete);
    }
}