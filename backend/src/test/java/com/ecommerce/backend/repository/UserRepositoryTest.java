package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Tests for User repository")
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    @DisplayName("Finds an user by its email when successful")
    @Transactional
    void findByEmail() {
        User userToSave = createUser();
        User userSaved = userRepository.save(userToSave);

        User userFound = userRepository.findByEmail(userSaved.getEmail()).get();

        Assertions.assertNotNull(userFound);
        Assertions.assertEquals(userSaved, userFound);
    }

    @Test
    @DisplayName("Checks if an email is already taken when successful")
    void existsByEmail() {
        User userToSave = createUser();
        User userSaved = userRepository.save(userToSave);

        boolean userExists = userRepository.existsByEmail(userSaved.getEmail());

        Assertions.assertNotNull(userExists);
        Assertions.assertEquals(userExists, true);
    }


    public User createUser() {
        String rand = String.valueOf(new Random().nextInt());
        User user = User.builder()
                .email("gaticabulicas."+ rand + "@gmail.com")
                .password("string123")
                .birthDate(LocalDate.now())
                .name("Gato Boulos")
                .build();
        return user;
    }
}