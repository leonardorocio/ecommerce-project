package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.Shipment;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {
}
