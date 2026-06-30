package com.group.group2.pet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class OrderItemDto {
    private OrderItemDto() {
    }

    public record Request(
            @NotNull UUID orderId,
            @NotNull UUID petId,
            @NotBlank String petName,
            @NotNull @PositiveOrZero BigDecimal price
    ) {
    }

    public record Response(
            UUID id,
            UUID orderId,
            UUID petId,
            String petName,
            BigDecimal price,
            LocalDateTime createdAt
    ) {
    }
}
