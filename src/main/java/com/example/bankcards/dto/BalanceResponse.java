package com.example.bankcards.dto;

import java.math.BigDecimal;

public record BalanceResponse(Long cardId, String maskedNumber, BigDecimal balance) {
}
