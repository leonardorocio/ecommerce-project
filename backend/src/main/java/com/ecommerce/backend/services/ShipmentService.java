package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.ShipmentMapper;
import com.ecommerce.backend.models.Shipment;
import com.ecommerce.backend.payload.ShipmentRequestBody;
import com.ecommerce.backend.repository.ShipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ShipmentService {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Autowired
    private ShipmentMapper shipmentMapper;

    public List<Shipment> getShipments() {
        return shipmentRepository.findAll();
    }

    public Shipment getShipmentById(int id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No shipments found with this id"));
    }

    public Shipment postShipment(ShipmentRequestBody shipmentRequestBody) {
        Shipment shipment = shipmentMapper.mapToShipment(shipmentRequestBody);
        return shipmentRepository.save(shipment);
    }

    public Shipment updateShipment(ShipmentRequestBody shipmentRequestBody, int id) {
        Shipment updatedShipment = shipmentMapper.mapToShipment(shipmentRequestBody);
        updatedShipment.setShipmentId(id);
        return shipmentRepository.save(updatedShipment);
    }

    public void deleteShipment(int id) {
        Shipment toDeleteShipment = getShipmentById(id);
        shipmentRepository.delete(toDeleteShipment);
    }
}
