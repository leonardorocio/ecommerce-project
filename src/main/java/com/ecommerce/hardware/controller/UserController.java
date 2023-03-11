package com.ecommerce.hardware.controller;

import com.ecommerce.hardware.configuration.JWTGenerator;
import com.ecommerce.hardware.models.User;
import com.ecommerce.hardware.repository.UserRepository;
import com.ecommerce.hardware.request.*;
import com.ecommerce.hardware.services.CustomUserDetailsService;
import com.ecommerce.hardware.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {
    private final UserRepository userRepository;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private JWTGenerator jwtGenerator;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody @Valid UserPostRequestBody userPostRequestBody) {
        return new ResponseEntity<>(userService.createUser(userPostRequestBody), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserPutRequestBody userPutRequestBody) {
        return ResponseEntity.ok(userService.updateUser(userPutRequestBody));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        return new ResponseEntity<String>("Usuário deletado com sucesso", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/order")
    public ResponseEntity<User> addProductForUserOrder(@RequestBody @Valid OrderRequestBody orderRequestBody) {
        return ResponseEntity.ok(userService.addProductForUserOrder(orderRequestBody));
    }

    @PatchMapping("/order/edit")
    public ResponseEntity<User> deleteProductFromUserOrder(@RequestBody @Valid OrderRequestBody orderRequestBody) {
        return ResponseEntity.ok(userService.deleteProductFromUserOrder(orderRequestBody));
    }

    @PatchMapping("/change_password")
    public ResponseEntity<String> changeUserPassword(@RequestBody PasswordRequestBody passwordRequestBody) {
        userService.updateUserPassword(passwordRequestBody);
        return ResponseEntity.ok("User password changed successfully");
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<AuthRequestBody> login(@RequestBody PasswordRequestBody passwordRequestBody) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(passwordRequestBody.getEmail(),
                passwordRequestBody.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(passwordRequestBody.getEmail());
        String token = jwtGenerator.generateToken(userDetails);
        return ResponseEntity.ok(new AuthRequestBody(token));
    }
}
