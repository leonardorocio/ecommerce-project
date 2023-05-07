package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.Shipment;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository extends JpaRepository<Shipment, Integer> {

    @Query("select shipment from Shipment shipment where shipment.delivered = 0")
    Optional<List<Shipment>> findAllOnGoingShipments();
}
