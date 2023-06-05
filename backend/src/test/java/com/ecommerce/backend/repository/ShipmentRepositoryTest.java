package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.Shipment;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

@SpringBootTest
@DisplayName("Tests for shipment repository")
class ShipmentRepositoryTest {

    @Autowired
    private ShipperRepository shipperRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;

    @Test
    @DisplayName("Find all on going shipments when successful")
    @Transactional
    void findAllOnGoingShipments_WhenSuccessful() {
        Shipment shipmentToSave = createShipment();
        Shipment shipmentSaved = shipmentRepository.save(shipmentToSave);

        List<Shipment> shipmentList = shipmentRepository.findAllOnGoingShipments().get();

        Assertions.assertNotNull(shipmentList);
        Assertions.assertEquals(shipmentList.contains(shipmentSaved), true);
    }

    public Shipment createShipment() {
        Shipment shipment = Shipment.builder()
                .shippingPrice(10)
                .delivered(false)
                .expectedDeliveryDate(LocalDate.now())
                .shipper(shipperRepository.findById(1).get())
                .orders(orderRepository.findById(3).get())
                .build();
        return shipment;
    }
}