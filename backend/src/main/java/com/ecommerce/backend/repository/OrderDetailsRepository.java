package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    OrderDetails findByOrders(Orders orders);
}