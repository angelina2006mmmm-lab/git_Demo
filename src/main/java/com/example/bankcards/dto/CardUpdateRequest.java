package com.example.bankcards.dto;

import com.example.bankcards.entity.CardStatus;
import jakarta.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CardUpdateRequest(
        LocalDate expirationDate,
        CardStatus status,
        @DecimalMin(value = "0.00") BigDecimal balance
) {
}
