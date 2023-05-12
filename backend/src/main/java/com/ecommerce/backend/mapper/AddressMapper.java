package com.ecommerce.backend.mapper;

import com.ecommerce.backend.models.Address;
import com.ecommerce.backend.payload.AddressRequestBody;
import com.ecommerce.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    @Autowired
    private UserService userService;
    public Address mapToAddress(AddressRequestBody addressRequestBody) {
        Address address = Address.builder()
                .user(userService.getUserById(addressRequestBody.getUserId()))
                .zipCode(addressRequestBody.getZipCode())
                .state(addressRequestBody.getState())
                .city(addressRequestBody.getCity())
                .streetWithNumber(addressRequestBody.getStreetWithNumber())
                .build();
        return address;
    }
}
