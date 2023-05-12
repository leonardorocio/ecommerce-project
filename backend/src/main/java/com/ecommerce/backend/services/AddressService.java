package com.ecommerce.backend.services;


import com.ecommerce.backend.exceptions.ResourceNotFoundException;
import com.ecommerce.backend.mapper.AddressMapper;
import com.ecommerce.backend.models.Address;
import java.util.List;

import com.ecommerce.backend.payload.AddressRequestBody;
import com.ecommerce.backend.repository.AddressRepository;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private UserService userService;

    public List<Address> getAddresses() {
        return addressRepository.findAll();
    }

    public Address getAddressById(int id) {
        return addressRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No such address"));
    }

    @Transactional
    public Address createAddress(AddressRequestBody addressRequestBody) {
        Address address = addressMapper.mapToAddress(addressRequestBody);
        return addressRepository.save(address);
    }


    public void deleteAddress(int id) {
        Address addressToDelete = getAddressById(id);
        addressRepository.delete(addressToDelete);
    }

    @Transactional
    public Address updateAddress(AddressRequestBody addressRequestBody, int id) {
        Address address = getAddressById(id);
        Address updatedAddress = addressMapper.mapToAddress(addressRequestBody);
        updatedAddress.setAddressId(address.getAddressId());
        return addressRepository.save(updatedAddress);
    }
}
