package com.ecommerce.hardware.services;

import com.ecommerce.hardware.exceptions.BadRequestException;
import com.ecommerce.hardware.models.Product;
import com.ecommerce.hardware.models.User;
import com.ecommerce.hardware.repository.UserRepository;
import com.ecommerce.hardware.request.OrderRequestBody;
import com.ecommerce.hardware.request.PasswordRequestBody;
import com.ecommerce.hardware.request.UserPostRequestBody;

import com.ecommerce.hardware.request.UserPatchRequestBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private ProductService productService;

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User createUser(UserPostRequestBody userPostRequestBody) {
        if (userRepository.existsByEmail(userPostRequestBody.getEmail())) {
            throw new BadRequestException("This email already exists");
        }
        if (userPostRequestBody.getPassword() == null) {
            throw new BadRequestException("Password cannot be null");
        }
        User newUser = User.builder()
                .email(userPostRequestBody.getEmail())
                .name(userPostRequestBody.getName())
                .date(userPostRequestBody.getDate())
                .password(passwordEncoder.encode(userPostRequestBody.getPassword()))
                .build();
        return userRepository.save(newUser);
    }

    public User getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> new BadRequestException("No id found"));
    }

    public User updateUser(UserPatchRequestBody userPatchRequestBody) {
        User user = getUserById(userPatchRequestBody.getUser_id());
        user.setCep(userPatchRequestBody.getCep());
        user.setName(userPatchRequestBody.getName());
        user.setDate(userPatchRequestBody.getDate());
        return userRepository.save(user);
    }

    public void deleteUser(Integer id) {
        User user = getUserById(id);
        userRepository.delete(user);
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
        if (user.getProductList().contains(order)) {
            user.getProductList().remove(order);
        } else {
            throw new BadRequestException("This user's order does not contain this product");
        }

        return userRepository.save(user);
    }

    public void updateUserPassword(PasswordRequestBody passwordRequestBody) {
        User user = getUserById(passwordRequestBody.getId());
        if (passwordRequestBody.getEmail().equals(user.getEmail())) {
            user.setPassword(passwordEncoder.encode(passwordRequestBody.getPassword()));
        } else {
            throw new BadRequestException("Email does not correspond to the id");
        }
        userRepository.save(user);
    }
}
