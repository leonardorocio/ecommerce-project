package com.ecommerce.backend.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer refreshTokenId;

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private Instant expiryDate;

    @Column(nullable = false, unique = true)
    private String token;
}
