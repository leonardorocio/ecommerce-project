package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.Address;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MethodArgumentNotValidException;
import java.util.List;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Tests for Address Repository")
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Save persists an Address when successful")
    public void save_PersistAddress_WhenSuccessful() {
        Address addressToSave = createMockAddress();
        Address addressSaved = addressRepository.save(addressToSave);
        Assertions.assertNotNull(addressSaved);
        Assertions.assertEquals(addressSaved.getUser(), addressToSave.getUser());
        Assertions.assertNotNull(addressSaved.getAddressId());
    }

    @Test
    @DisplayName("Save throws error for an Address when failed")
    public void save_ThrowsErrorAddress_WhenFailed() {
        Address addressToSave = createMockAddress();
        addressToSave.setUser(null);
        Assertions.assertThrows(ConstraintViolationException.class, () -> {
            this.addressRepository.save(addressToSave);
        });
    }

    @Test
    @DisplayName("Save updates an Address when successful")
    public void save_UpdatesAddress_WhenSuccessful() {
        Address addressToSave = createMockAddress();
        Address addressSaved = addressRepository.save(addressToSave);

        addressSaved.setCity("Jaraguá do Sul");
        Address updatedAddress = addressRepository.save(addressSaved);

        Assertions.assertNotNull(updatedAddress);
        Assertions.assertEquals(addressSaved.getCity(), updatedAddress.getCity());
        Assertions.assertNotNull(updatedAddress.getAddressId());
    }

    @Test
    @DisplayName("Delete removes an Address when successful")
    public void delete_RemovesAddress_WhenSuccessful() {
        Address addressToSave = createMockAddress();
        Address addressSaved = addressRepository.save(addressToSave);

        addressRepository.delete(addressSaved);

        Optional<Address> addressOptional = addressRepository.findById(addressSaved.getAddressId());

        Assertions.assertEquals(addressOptional.isEmpty(), true);
    }

    @Test
    @DisplayName("Lists an Address list when successful")
    @Transactional
    public void find_ListAddress_WhenSuccessful() {
        Address addressToSave = createMockAddress();
        Address addressSaved = addressRepository.save(addressToSave);

        List<Address> addressList = addressRepository.findAll();

        Assertions.assertEquals(addressList.isEmpty(), false);
        Assertions.assertEquals(addressList.contains(addressSaved), true);
    }


    private Address createMockAddress() {
        Address address = Address.builder()
                .city("Joinville")
                .state("Santa Catarina")
                .streetWithNumber("Teste, 123")
                .zipCode("83937400")
                .user(userRepository.findById(3).get())
                .build();
        return address;
    }
}