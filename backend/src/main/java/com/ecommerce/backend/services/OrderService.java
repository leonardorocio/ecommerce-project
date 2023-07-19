package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.OrderMapper;
import com.ecommerce.backend.mapper.PatchMapper;
import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.models.Orders;
import com.ecommerce.backend.models.Product;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.payload.OrderRequestBody;
import com.ecommerce.backend.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    public List<Orders> getOrders() {
        return orderRepository.findAll();
    }

    public List<Orders> getOpenOrders() {
        return orderRepository.findAllOpenOrders().orElseThrow(() ->
                new ResourceNotFoundException("Nenhum pedido em aberto"));
    }

    public Orders getOrderById(int id) {
        return orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Pedido não encontrado"));
    }

    public Orders postOrder(OrderRequestBody orderRequestBody) {
        Orders orders = orderMapper.mapToOrder(orderRequestBody);
        return orderRepository.save(orders);
    }

    public Orders updateOrder(OrderRequestBody orderRequestBody, int id) {
        Orders originalOrders = getOrderById(id);
        Orders updatedOrders = orderMapper.mapToOrder(orderRequestBody);
        updatedOrders.setId(originalOrders.getId());
        return orderRepository.save(updatedOrders);
    }

    public void updateOrderTotalPrice(int id, double totalPrice) {
        Orders order = getOrderById(id);
        order.setTotalPrice(order.getTotalPrice() + totalPrice);
        orderRepository.updateTotalPrice(id, order.getTotalPrice());
    }

    public void deleteOrder(int id) {
        Orders ordersToDelete = getOrderById(id);
        orderRepository.delete(ordersToDelete);
    }

    public Orders closeOrder(int id) {
        Orders order = getOrderById(id);
        order.setClosed(true);
        return orderRepository.save(order);
    }
}
