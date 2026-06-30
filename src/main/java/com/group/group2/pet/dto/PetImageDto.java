package com.group.group2.pet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public final class PetImageDto {
    private PetImageDto() {
    }

    public record Request(
            @NotNull UUID petId,
            @NotBlank String imageUrl,
            Boolean thumbnail
    ) {
    }

    public record Response(
            UUID id,
            UUID petId,
            String imageUrl,
            Boolean thumbnail,
            LocalDateTime createdAt
    ) {
    }
}
