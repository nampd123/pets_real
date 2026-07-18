package com.group.group2.pet.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public final class FavoriteDto {
    private FavoriteDto() {
    }

    public record Request(
            @NotNull UUID userId,
            @NotNull UUID petId
    ) {
    }

    public record Response(
            UUID id,
            UUID userId,
            UUID petId,
            LocalDateTime createdAt
    ) {
    }
}
