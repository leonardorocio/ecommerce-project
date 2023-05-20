package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.Comment;
import com.ecommerce.backend.models.OrderDetails;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@DisplayName("Tests for OrderDetails Repository")
class OrderDetailsRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;


    @Test
    @DisplayName("Save persists an OrderDetails when successful")
    public void save_PersistOrderDetails_WhenSuccessful() {
        OrderDetails mockOrderDetails = createMockOrderDetails();
        OrderDetails orderDetailsSaved = orderDetailsRepository.save(mockOrderDetails);

        Assertions.assertNotNull(orderDetailsSaved);
        Assertions.assertEquals(orderDetailsSaved.getQuantity(), orderDetailsSaved.getQuantity());
        Assertions.assertNotNull(orderDetailsSaved.getOrderDetailsId());
    }

    @Test
    @DisplayName("Save throws error for an OrderDetails when failed")
    public void save_ThrowsErrorOrderDetails_WhenFailed() {
        OrderDetails orderDetailsToSave = createMockOrderDetails();
        orderDetailsToSave.setQuantity(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.orderDetailsRepository.save(orderDetailsToSave);
        });
    }

    @Test
    @DisplayName("Save updates an OrderDetails when successful")
    public void save_UpdatesOrderDetails_WhenSuccessful() {
        OrderDetails orderDetailsToSave = createMockOrderDetails();
        OrderDetails orderDetailsSaved = orderDetailsRepository.save(orderDetailsToSave);

        orderDetailsSaved.setQuantity(3);
        OrderDetails updatedOrderDetails = orderDetailsRepository.save(orderDetailsSaved);

        Assertions.assertNotNull(updatedOrderDetails);
        Assertions.assertEquals(orderDetailsSaved.getQuantity(), updatedOrderDetails.getQuantity());
        Assertions.assertNotNull(updatedOrderDetails.getOrderDetailsId());
    }

    @Test
    @DisplayName("Delete removes an OrderDetails when successful")
    public void delete_RemovesOrderDetails_WhenSuccessful() {
        OrderDetails orderDetailsToSave = createMockOrderDetails();
        OrderDetails orderDetailsSaved = orderDetailsRepository.save(orderDetailsToSave);

        orderDetailsRepository.delete(orderDetailsSaved);

        Optional<OrderDetails> orderDetailsOptional = orderDetailsRepository.findById(orderDetailsSaved.getOrderDetailsId());

        Assertions.assertEquals(orderDetailsOptional.isEmpty(), true);
    }

    @Test
    @DisplayName("Finds an OrderDetails that matches order id and product id when successful")
    @Transactional
    public void find_ListUserComment_WhenSuccessful() {
        OrderDetails orderDetailsToSave = createMockOrderDetails();
        OrderDetails orderDetailsSaved = orderDetailsRepository.save(orderDetailsToSave);

        boolean existsTest = orderDetailsRepository.existsOrderWithSameProduct(3, 1);

        Assertions.assertNotNull(existsTest);
    }

    @Test
    @DisplayName("Lists an OrderDetails list when successful")
    @Transactional
    public void find_ListProductComment_WhenSuccessful() {
        OrderDetails orderDetailsToSave = createMockOrderDetails();
        OrderDetails orderDetailsSaved = orderDetailsRepository.save(orderDetailsToSave);

        List<OrderDetails> orderDetailsList = orderDetailsRepository.findAll();

        Assertions.assertEquals(orderDetailsList.isEmpty(), false);
        Assertions.assertEquals(orderDetailsList.contains(orderDetailsSaved), true);
    }

    private OrderDetails createMockOrderDetails() {
        OrderDetails orderDetails = OrderDetails.builder()
                .quantity(1)
                .product(productRepository.findById(1).get())
                .orders(orderRepository.findById(3).get())
                .build();
        return orderDetails;
    }
}