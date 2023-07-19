package com.ecommerce.backend.mapper;

import com.ecommerce.backend.models.Address;
import com.ecommerce.backend.payload.AddressRequestBody;
import com.ecommerce.backend.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddressMapper {


    private final UserService userService;
    public Address mapToAddress(AddressRequestBody addressRequestBody) {
        Address address = Address.builder()
                .user(userService.getUserById(addressRequestBody.getUserId()))
                .zipCode(addressRequestBody.getZipCode())
                .state(addressRequestBody.getState())
                .city(addressRequestBody.getCity())
                .street(addressRequestBody.getStreet())
                .number(addressRequestBody.getNumber())
                .build();
        return address;
    }
}
