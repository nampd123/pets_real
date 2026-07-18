package com.group.group2.pet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class OrderDto {
    private OrderDto() {
    }

    public record Request(
            @NotNull UUID customerId,
            @NotNull UUID shopId,
            @NotNull @PositiveOrZero BigDecimal totalAmount,
            @PositiveOrZero BigDecimal shippingFee,
            @NotNull @PositiveOrZero BigDecimal finalAmount,
            @NotBlank String receiverName,
            @NotBlank String receiverPhone,
            @NotBlank String receiverAddress,
            String note,
            String status
    ) {
    }

    public record Response(
            UUID id,
            UUID customerId,
            UUID shopId,
            BigDecimal totalAmount,
            BigDecimal shippingFee,
            BigDecimal finalAmount,
            String receiverName,
            String receiverPhone,
            String receiverAddress,
            String note,
            String status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
    }
}
