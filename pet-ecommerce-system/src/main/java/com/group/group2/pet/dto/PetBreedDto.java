package com.group.group2.pet.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public final class PetBreedDto {
    private PetBreedDto() {
    }

    public record Request(
            @NotNull UUID speciesId,
            @NotBlank String name,
            String description
    ) {
    }

    public record Response(
            UUID id,
            UUID speciesId,
            String name,
            String description,
            LocalDateTime createdAt
    ) {
    }
}
