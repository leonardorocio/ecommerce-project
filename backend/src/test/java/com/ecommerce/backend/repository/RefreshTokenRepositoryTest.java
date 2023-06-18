package com.ecommerce.backend.repository;

import com.ecommerce.backend.models.RefreshToken;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Tests for RefreshTokenRepository")
class RefreshTokenRepositoryTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Test
    @DisplayName("find RefreshToken by Token when successful")
    @Transactional
    void findByToken_WhenSuccessful() {
        RefreshToken refreshTokenToSave = createRefreshToken();
        RefreshToken refreshTokenSaved = refreshTokenRepository.save(refreshTokenToSave);

        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenToSave.getToken()).get();

        Assertions.assertNotNull(refreshToken);
        Assertions.assertEquals(refreshToken, refreshTokenToSave);
    }

    @Test
    @Transactional
    @DisplayName("delete remove RefreshToken by User when successful")
    void deleteByUser_WhenSuccessful() {
        RefreshToken refreshTokenToSave = createRefreshToken();
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenToSave.getToken()).get();

        refreshTokenRepository.deleteByUserOwner(refreshToken.getUserOwner());

        Optional<RefreshToken> refreshTokenOptional = refreshTokenRepository
                .findById(refreshToken.getRefreshTokenId());

        Assertions.assertEquals(refreshTokenOptional.isEmpty(), true);
    }

    public RefreshToken createRefreshToken() {
        RefreshToken refreshToken = RefreshToken.builder()
                .expiryDate(Instant.now())
                .token("esejaoie-aosndoaid-fieqonf-sfsdfs")
                .userOwner(userRepository.findById(3).get())
                .build();
        return refreshToken;
    }
}