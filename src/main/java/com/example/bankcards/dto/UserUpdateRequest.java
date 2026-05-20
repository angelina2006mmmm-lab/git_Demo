package com.example.bankcards.dto;

import jakarta.validation.constraints.Size;
import java.util.Set;

public record UserUpdateRequest(
        @Size(min = 6, message = "Password must contain at least 6 characters") String password,
        String fullName,
        Set<String> roles,
        Boolean enabled
) {
}
