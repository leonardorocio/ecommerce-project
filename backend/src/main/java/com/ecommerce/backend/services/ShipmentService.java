package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.ShipmentMapper;
import com.ecommerce.backend.models.Shipment;
import com.ecommerce.backend.payload.ShipmentRequestBody;
import com.ecommerce.backend.repository.ShipmentRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Log4j2
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentMapper shipmentMapper;

    public List<Shipment> getShipments() {
        return shipmentRepository.findAll();
    }

    public List<Shipment> getOnGoingShipments() {
        return shipmentRepository.findAllOnGoingShipments().orElseThrow(() ->
                new ResourceNotFoundException("No on going shipments"));
    }

    public Shipment getShipmentById(int id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No shipments found with this id"));
    }

    @Transactional
    public Shipment postShipment(ShipmentRequestBody shipmentRequestBody) {
        Shipment shipment = shipmentMapper.mapToShipment(shipmentRequestBody);
        return shipmentRepository.save(shipment);
    }

    @Transactional
    public Shipment updateShipment(ShipmentRequestBody shipmentRequestBody, int id) {
        Shipment originalShipment = getShipmentById(id);
        Shipment updatedShipment = shipmentMapper.mapToShipment(shipmentRequestBody);
        updatedShipment.setShipmentId(originalShipment.getShipmentId());
        return shipmentRepository.save(updatedShipment);
    }

    public void deleteShipment(int id) {
        Shipment toDeleteShipment = getShipmentById(id);
        log.info(toDeleteShipment.getShipmentId());
        shipmentRepository.delete(toDeleteShipment);
    }
}
