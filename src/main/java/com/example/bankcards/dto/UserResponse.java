package com.example.bankcards.dto;

import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        String fullName,
        boolean enabled,
        Set<String> roles
) {
}
