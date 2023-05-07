package com.ecommerce.backend.repository;

import java.util.List;
import java.util.Optional;

import com.ecommerce.backend.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

    @Query("select orders from Orders orders where orders.closed = 0")
    Optional<List<Orders>> findAllOpenOrders();

    @Modifying
    @Query("update Orders ord set ord.totalPrice = :total_price where ord.orderId = :id")
    void updateTotalPrice(@Param("id") int id, @Param("total_price") double totalPrice);
}
