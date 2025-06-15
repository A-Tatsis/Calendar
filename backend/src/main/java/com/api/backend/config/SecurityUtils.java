package com.api.backend.config;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.UUID;

public class SecurityUtils {

    public static UUID getCurrentUserId() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt jwt) {
            return UUID.fromString(jwt.getClaimAsString("sub"));
        }

        throw new IllegalStateException("User ID (sub) not found in JWT");
    }

    public static String gerCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof Jwt jwt) {
            return jwt.getClaimAsString("preferred_username");
        }

        throw new IllegalStateException("Username (preferred_username) not found in JWT");
    }
}
