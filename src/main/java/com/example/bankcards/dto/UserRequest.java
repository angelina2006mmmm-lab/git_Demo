package com.example.bankcards.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record UserRequest(
        @NotBlank String username,
        @NotBlank @Size(min = 6, message = "Password must contain at least 6 characters") String password,
        @NotBlank String fullName,
        @NotEmpty Set<String> roles,
        boolean enabled
) {
}
