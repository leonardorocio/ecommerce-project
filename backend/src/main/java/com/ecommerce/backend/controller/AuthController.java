package com.ecommerce.backend.controller;

import com.ecommerce.backend.configuration.JWTGenerator;
import com.ecommerce.backend.payload.AuthRequestBody;
import com.ecommerce.backend.payload.PasswordRequestBody;
import com.ecommerce.backend.services.CustomUserDetailsService;
import com.ecommerce.backend.services.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
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
    private JWTGenerator jwtGenerator;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/login")
    @CrossOrigin(origins = "*", allowedHeaders = "*")
    public ResponseEntity<AuthRequestBody> login(@RequestBody @Valid PasswordRequestBody passwordRequestBody) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(passwordRequestBody.getEmail(),
                        passwordRequestBody.getPassword()));
        UserDetails userDetails = userDetailsService.loadUserByUsername(passwordRequestBody.getEmail());
        String token = jwtGenerator.generateToken(userDetails);
        log.info(authentication.getDetails());
//        RefreshToken refreshToken = refreshTokenService.createRefreshToken(authentication.getPrincipal())
        return ResponseEntity.ok(new AuthRequestBody(token));
    }
}
