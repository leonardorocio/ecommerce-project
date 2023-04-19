package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.RefreshTokenException;
import com.ecommerce.backend.models.RefreshToken;
import com.ecommerce.backend.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    @Value("${jwt.expiration}")
    private Integer refreshTokenDuration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Integer userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .user(userService.getUserById(userId))
                .expiryDate(Instant.now().plusMillis(refreshTokenDuration))
                .token(UUID.randomUUID().toString())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException("Refresh token invalid");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Integer userId) {
        return refreshTokenRepository.deleteByUser(userService.getUserById(userId));
    }
}