package com.ecommerce.backend.controller;

import com.ecommerce.backend.configuration.JWTGenerator;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.payload.*;
import com.ecommerce.backend.services.CustomUserDetailsService;
import com.ecommerce.backend.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Log4j2
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody UserPostRequestBody userPostRequestBody) {
        return new ResponseEntity<>(userService.createUser(userPostRequestBody), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserPatchRequestBody userPatchRequestBody,
                                           @PathVariable Integer id) {
        return ResponseEntity.ok(userService.updateUser(userPatchRequestBody, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id) {
        return new ResponseEntity<String>("Usuário deletado com sucesso", HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/change_password/{id}")
    public ResponseEntity<String> changeUserPassword(@RequestBody @Valid PasswordRequestBody passwordRequestBody,
                                                     @PathVariable Integer id) {
        userService.updateUserPassword(passwordRequestBody, id);
        return ResponseEntity.ok("User password changed successfully");
    }
}
