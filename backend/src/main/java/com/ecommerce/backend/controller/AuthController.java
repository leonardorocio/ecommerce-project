package com.ecommerce.backend.controller;

import com.ecommerce.backend.configuration.CustomUserDetails;
import com.ecommerce.backend.configuration.JWTGenerator;
import com.ecommerce.backend.exceptions.RefreshTokenException;
import com.ecommerce.backend.models.RefreshToken;
import com.ecommerce.backend.payload.PasswordRequestBody;
import com.ecommerce.backend.payload.TokenRequestBody;
import com.ecommerce.backend.payload.TokenResponse;
import com.ecommerce.backend.services.CustomUserDetailsService;
import com.ecommerce.backend.services.RefreshTokenService;
import com.ecommerce.backend.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Log4j2
@Tag(name = "Authentication", description = "Describes the login and RefreshToken operations")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;


    @Autowired
    private JWTGenerator jwtGenerator;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @Operation(summary = "Authenticates the user",
            description = "Takes a PasswordRequestBody and authenticates them")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the user authenticated"),
            @ApiResponse(responseCode = "400", description = "Invalid arguments"),
            @ApiResponse(responseCode = "401", description = "Authentication failed")
    })
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid PasswordRequestBody passwordRequestBody) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(passwordRequestBody.getEmail(),
                        passwordRequestBody.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(passwordRequestBody.getEmail());
        String token = jwtGenerator.generateToken(userDetails);

        log.info(userDetails.getUserId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUserId());
        userService.updateUserRefreshToken(userService.getUserById(userDetails.getUserId()), refreshToken);
        return ResponseEntity.ok(
                new TokenResponse(token, refreshToken.getToken(),
                        jwtGenerator.extractExpiration(token).toInstant(), refreshToken.getUser())
        );
    }

    @PostMapping("/refreshtoken")
    @Operation(summary = "Returns to the user a new accessToken",
            description = "Takes the user's RefreshToken and then returns the new accessToken")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Returns the AccessToken and RefreshToken"),
            @ApiResponse(responseCode = "401", description = "Invalid Token")
    })
    public ResponseEntity<TokenResponse> getRefreshToken(@Valid @RequestBody TokenRequestBody requestBody)  {
        String refreshToken = requestBody.getRefreshToken();
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtGenerator.generateToken(user.getEmail());
                    return ResponseEntity.ok(new TokenResponse(token, refreshToken));
                }).orElseThrow(() -> new RefreshTokenException("Invalid Refresh Token"));
    }

}
