package com.ecommerce.hardware.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {

    @Value("${jwt.expiration}")
    public static long JWT_EXPIRATION;

    @Value("${jwt.secret}")
    public static String JWT_SECRET;
}
