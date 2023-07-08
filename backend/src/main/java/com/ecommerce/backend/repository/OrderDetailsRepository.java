package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer> {
    @Query("select count(OD) > 0 from OrderDetails OD where OD.orders.id = :orderId and OD.product.id = :productId")
    boolean existsOrderWithSameProduct(@Param("orderId") Integer orderId, @Param("productId") Integer productId);
}
