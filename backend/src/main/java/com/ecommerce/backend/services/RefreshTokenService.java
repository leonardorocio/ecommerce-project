package com.ecommerce.backend.services;

import com.ecommerce.backend.exceptions.RefreshTokenException;
import com.ecommerce.backend.models.RefreshToken;
import com.ecommerce.backend.repository.RefreshTokenRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    @Value("${jwt.expiration}")
    private Integer refreshTokenDuration;

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private  UserService userService;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Integer userId) {
        RefreshToken refreshToken = RefreshToken.builder()
                .userOwner(userService.getUserById(userId))
                .expiryDate(Instant.now().plusMillis(refreshTokenDuration * 10))
                .token(UUID.randomUUID().toString())
                .build();

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token){
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new RefreshTokenException("Refresh Token inválido");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(Integer userId) {
        return refreshTokenRepository.deleteByUserOwner(userService.getUserById(userId));
    }
}
