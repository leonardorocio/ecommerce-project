package com.ecommerce.backend.controller;

import com.ecommerce.backend.configuration.JWTGenerator;
import com.ecommerce.backend.models.User;
import com.ecommerce.backend.repository.UserRepository;
import com.ecommerce.backend.payload.*;
import com.ecommerce.backend.services.CustomUserDetailsService;
import com.ecommerce.backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
@Log4j2
@Tag(name = "User", description = "Describes the User related operations")
@SecurityRequirement(name = "Bearer Authentication")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    @Operation(summary = "Returns all the Users in the database",
            description = "Takes no parameters, returns all the Users in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the list of Users"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<List<User>> getUsers() {
        return ResponseEntity.ok(userService.getUsers());
    }

    @PostMapping
    @Operation(summary = "Creates a User",
            description = "Takes a UserPostRequestBody, maps to a User and saves in the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Returns the user saved in the database"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<User> createUser(@Valid @RequestBody UserPostRequestBody userPostRequestBody) {
        return new ResponseEntity<>(userService.createUser(userPostRequestBody), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns a User based on id",
            description = "Takes a user's id and returns the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the User"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<User> getUserById(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Updates a User",
            description = "Takes the user's id and a UserPatchRequestBody, maps to a User and updates it")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User updated successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<User> updateUser(@RequestBody @Valid UserPatchRequestBody userPatchRequestBody,
                                           @PathVariable Integer id) {
        return ResponseEntity.ok(userService.updateUser(userPatchRequestBody, id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a User",
            description = "Takes the user's id and deletes the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/change_password/{id}")
    @Operation(summary = "Deletes a User",
            description = "Takes the user's id and a PasswordRequestBody, and changes the user's password")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User password changed successfully"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "400", description = "Invalid Arguments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<String> changeUserPassword(@RequestBody @Valid PasswordRequestBody passwordRequestBody,
                                                     @PathVariable Integer id) {
        userService.updateUserPassword(passwordRequestBody, id);
        return ResponseEntity.ok("User password changed successfully");
    }
}
