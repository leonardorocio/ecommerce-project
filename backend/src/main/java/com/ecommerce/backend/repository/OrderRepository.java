package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.OrderDetails;
import com.ecommerce.backend.models.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {


}
