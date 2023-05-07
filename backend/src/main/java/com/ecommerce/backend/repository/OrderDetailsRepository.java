package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    @Query("select OD from OrderDetails OD where OD.orders.orderId = :orderId and OD.product.productId = :productId")
    OrderDetails existsOrderWithSameProduct(@Param("orderId") Integer orderId, @Param("productId") Integer productId);
}
