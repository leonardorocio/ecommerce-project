package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.ShipperMapper;
import com.ecommerce.backend.models.Shipment;
import com.ecommerce.backend.models.Shipper;
import com.ecommerce.backend.payload.ShipperRequestBody;
import com.ecommerce.backend.repository.ShipperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class ShipperService {

    @Autowired
    private ShipperRepository shipperRepository;

    @Autowired
    private ShipperMapper shipperMapper;

    public List<Shipper> getShippers() {
        return shipperRepository.findAll();
    }

    public Shipper getShipperById(int id) {
        return shipperRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such shipper"));
    }

    public List<Shipment> getShippersOnGoingShipments(int id) {
        Shipper shipper = getShipperById(id);
        return shipper.getShipmentList().stream()
                .filter(Predicate.not(Shipment::isDelivered))
                .collect(Collectors.toList());
    }

    public Shipper postShipper(ShipperRequestBody shipperRequestBody) {
        Shipper shipper = shipperMapper.mapToShipper(shipperRequestBody);
        return shipperRepository.save(shipper);
    }

    public void deleteShipper(int id) {
        Shipper shipperToDelete = getShipperById(id);
        shipperRepository.delete(shipperToDelete);
    }


    public Shipper updateShipper(ShipperRequestBody shipperRequestBody, int id) {
        Shipper originalShipper = getShipperById(id);
        Shipper updatedShipper = shipperMapper.mapToShipper(shipperRequestBody);
        updatedShipper.setShipperId(originalShipper.getShipperId());
        return shipperRepository.save(updatedShipper);
    }
}
