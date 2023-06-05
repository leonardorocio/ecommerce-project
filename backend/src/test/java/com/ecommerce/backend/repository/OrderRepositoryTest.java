package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.Orders;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DisplayName("Tests for Order Repository")
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Test
    @DisplayName("Finds all the open orders when successful")
    void findAllOpenOrders_WhenSuccessful() {
        Orders ordersToSave = createMockOrder();
        ordersToSave.setClosed(true);
        Orders orderSaved = orderRepository.save(ordersToSave);
        List<Orders> orderOpen = orderRepository.findAllOpenOrders().get();

        Assertions.assertNotNull(orderOpen);
        Assertions.assertEquals(orderOpen.contains(orderSaved), true);
    }

    @Test
    @DisplayName("Updates an orders total price when successful")
    void updateTotalPrice_WhenSuccessful() {
        Orders ordersToSave = createMockOrder();
        Orders orderSaved = orderRepository.save(ordersToSave);

        orderRepository.updateTotalPrice(orderSaved.getOrderId(), 120);

        Orders updatedOrder = orderRepository.findById(orderSaved.getOrderId()).get();

        Assertions.assertEquals(updatedOrder.getTotalPrice(), 120);
        Assertions.assertNotNull(updatedOrder);
    }

    public Orders createMockOrder() {
        Orders orders = Orders.builder()
                .orderedDate(LocalDate.now())
                .closed(false)
                .customer(userRepository.findById(3).get())
                .totalPrice(100)
                .shipment(shipmentRepository.findById(5).get())
                .build();
        return orders;
    }
}