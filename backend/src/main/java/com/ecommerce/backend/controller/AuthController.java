package com.ecommerce.backend.controller;

import com.ecommerce.backend.configuration.CustomUserDetails;
import com.ecommerce.backend.configuration.JWTGenerator;
import com.ecommerce.backend.exceptions.RefreshTokenException;
import com.ecommerce.backend.models.RefreshToken;
import com.ecommerce.backend.payload.PasswordRequestBody;
import com.ecommerce.backend.payload.RefreshTokenRequestBody;
import com.ecommerce.backend.payload.RefreshTokenResponse;
import com.ecommerce.backend.services.CustomUserDetailsService;
import com.ecommerce.backend.services.RefreshTokenService;
import com.ecommerce.backend.services.UserService;
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
    public ResponseEntity<RefreshTokenResponse> login(@RequestBody @Valid PasswordRequestBody passwordRequestBody) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(passwordRequestBody.getEmail(),
                        passwordRequestBody.getPassword()));
        CustomUserDetails userDetails = (CustomUserDetails) userDetailsService.loadUserByUsername(passwordRequestBody.getEmail());
        String token = jwtGenerator.generateToken(userDetails);

        log.info(userDetails.getUserId());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUserId());
        userService.updateUserRefreshToken(userService.getUserById(userDetails.getUserId()), refreshToken);
        return ResponseEntity.ok(new RefreshTokenResponse(token, refreshToken.getToken()));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<RefreshTokenResponse> getRefreshToken(@Valid @RequestBody RefreshTokenRequestBody requestBody)  {
        String refreshToken = requestBody.getRequestToken();
        return refreshTokenService.findByToken(refreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    String token = jwtGenerator.generateToken(user.getEmail());
                    return ResponseEntity.ok(new RefreshTokenResponse(token, refreshToken));
                }).orElseThrow(() -> new RefreshTokenException("Invalid Refresh Token"));
    }

}
