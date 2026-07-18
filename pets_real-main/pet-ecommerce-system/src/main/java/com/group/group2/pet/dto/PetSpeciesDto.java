package com.group.group2.pet.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

public final class PetSpeciesDto {
    private PetSpeciesDto() {
    }

    public record Request(
            @NotBlank String name,
            String description
    ) {
    }

    public record Response(
            UUID id,
            String name,
            String description,
            LocalDateTime createdAt
    ) {
    }
}
