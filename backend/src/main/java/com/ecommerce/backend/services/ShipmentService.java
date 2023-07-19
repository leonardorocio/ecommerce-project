package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.ShipmentMapper;
import com.ecommerce.backend.models.Shipment;
import com.ecommerce.backend.payload.ShipmentRequestBody;
import com.ecommerce.backend.repository.ShipmentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class ShipmentService {

    private final ShipmentRepository shipmentRepository;

    private final ShipmentMapper shipmentMapper;

    public List<Shipment> getShipments() {
        return shipmentRepository.findAll();
    }

    public List<Shipment> getOnGoingShipments() {
        return shipmentRepository.findAllOnGoingShipments().orElseThrow(() ->
                new ResourceNotFoundException("Sem entregas em andamento"));
    }

    public Shipment getShipmentById(int id) {
        return shipmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhuma entrega existente com esse ID"));
    }

    @Transactional
    public Shipment postShipment(ShipmentRequestBody shipmentRequestBody) {
        Shipment shipment = shipmentMapper.mapToShipment(shipmentRequestBody);
        return shipmentRepository.save(shipment);
    }

    @Transactional
    public Shipment updateShipment(ShipmentRequestBody shipmentRequestBody, int id) {
        Shipment originalShipment = getShipmentById(id);
        if (originalShipment.isDelivered()) {
            throw new BadRequestException("Não é possível atualizar entrega já efetuada");
        }
        Shipment updatedShipment = shipmentMapper.mapToShipment(shipmentRequestBody);
        updatedShipment.setId(originalShipment.getId());
        return shipmentRepository.save(updatedShipment);
    }

    public void deleteShipment(int id) {
        Shipment toDeleteShipment = getShipmentById(id);
        shipmentRepository.delete(toDeleteShipment);
    }

    public Shipment closeShipment(int id) {
        Shipment shipment = getShipmentById(id);
        shipment.setDelivered(true);
        return shipmentRepository.save(shipment);
    }
}
