package com.group.group2.pet.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public final class CartDto {
    private CartDto() {
    }

    public record Request(
            @NotNull UUID userId
    ) {
    }

    public record Response(
            UUID id,
            UUID userId,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
    }
}
