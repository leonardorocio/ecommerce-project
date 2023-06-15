package com.ecommerce.backend.mapper;

import com.ecommerce.backend.exceptions.BadRequestException;
import com.ecommerce.backend.models.Shipper;
import com.ecommerce.backend.payload.ShipperRequestBody;
import org.springframework.stereotype.Component;

@Component
public class ShipperMapper {

    public Shipper mapToShipper(ShipperRequestBody shipperRequestBody) {
        if (validateShipper(shipperRequestBody)) {
            Shipper shipper = Shipper.builder()
                    .name(shipperRequestBody.getName())
                    .fixedTax(shipperRequestBody.getFixedTax())
                    .build();
            return shipper;
        }
        return null;
    }

    public boolean validateShipper(ShipperRequestBody shipperRequestBody) {
        if (shipperRequestBody.getFixedTax() < 0) {
            throw new BadRequestException("Taxa fixa não pode ser menor que 0");
        }
        return true;
    }
}
