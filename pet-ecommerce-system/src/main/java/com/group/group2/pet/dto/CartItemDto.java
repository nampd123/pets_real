package com.group.group2.pet.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class CartItemDto {
    private CartItemDto() {
    }

    public record Request(
            @NotNull UUID cartId,
            @NotNull UUID petId,
            @NotNull Integer quantity,
            @NotNull @PositiveOrZero BigDecimal price
    ) {
    }

    public record Response(
            UUID id,
            UUID cartId,
            UUID petId,
            Integer quantity,
            BigDecimal price,
            LocalDateTime createdAt
    ) {
    }
}
