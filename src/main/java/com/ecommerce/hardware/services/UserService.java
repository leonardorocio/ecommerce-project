package com.ecommerce.hardware.services;

import com.ecommerce.hardware.models.Product;
import com.ecommerce.hardware.models.User;
import com.ecommerce.hardware.repository.UserRepository;
import com.ecommerce.hardware.request.OrderRequestBody;
import com.ecommerce.hardware.request.PasswordRequestBody;
import com.ecommerce.hardware.request.UserPostRequestBody;

import com.ecommerce.hardware.request.UserPutRequestBody;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductService productService;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserPostRequestBody userPostRequestBody) {
        if (userRepository.existsByEmail(userPostRequestBody.getEmail())) {
            throw new ValidationException("This email already exists");
        }
        User newUser = User.builder()
                .email(userPostRequestBody.getEmail())
                .name(userPostRequestBody.getName())
                .date(userPostRequestBody.getDate())
                .password(new BCryptPasswordEncoder().encode(userPostRequestBody.getPassword()))
                .build();
        return userRepository.save(newUser);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("No id found"));
    }

    public User updateUser(UserPutRequestBody userPutRequestBody) {
        User user = getUserById(userPutRequestBody.getUser_id());
        user.setCep(userPutRequestBody.getCep());
        user.setName(userPutRequestBody.getName());
        user.setDate(userPutRequestBody.getDate());
        return userRepository.save(user);
    }

    public User deleteUser(Integer id) {
        User user = getUserById(id);
        userRepository.delete(user);
        return user;
    }

    public User addProductForUserOrder(OrderRequestBody orderRequestBody) {
        User user = getUserById(orderRequestBody.getUser_id());
        Product order = productService.getProductById(orderRequestBody.getProduct_id());
        user.getProductList().add(order);
        return userRepository.save(user);
    }

    public User deleteProductFromUserOrder(OrderRequestBody orderRequestBody) {
        User user = getUserById(orderRequestBody.getUser_id());
        Product order = productService.getProductById(orderRequestBody.getProduct_id());
        user.getProductList().remove(order);
        return userRepository.save(user);
    }

    public void updateUserPassword(PasswordRequestBody passwordRequestBody) {
        User user = getUserById(passwordRequestBody.getId());
        user.setPassword(new BCryptPasswordEncoder().encode(passwordRequestBody.getPassword()));
        userRepository.save(user);
    }
}
