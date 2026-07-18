package com.group.group2.pet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class PaymentDto {
    private PaymentDto() {
    }

    public record Request(
            @NotNull UUID orderId,
            @NotBlank String paymentMethod,
            @NotNull @PositiveOrZero BigDecimal amount,
            String status,
            String transactionCode,
            LocalDateTime paidAt
    ) {
    }

    public record Response(
            UUID id,
            UUID orderId,
            String paymentMethod,
            BigDecimal amount,
            String status,
            String transactionCode,
            LocalDateTime paidAt,
            LocalDateTime createdAt
    ) {
    }
}
