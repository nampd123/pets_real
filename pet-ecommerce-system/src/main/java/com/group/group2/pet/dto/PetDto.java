package com.group.group2.pet.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public final class PetDto {
    private PetDto() {
    }

    public record Request(
            @NotNull UUID shopId,
            @NotNull UUID speciesId,
            UUID breedId,
            @NotBlank String name,
            String gender,
            @Min(0) Integer ageMonths,
            String color,
            @PositiveOrZero BigDecimal weightKg,
            String description,
            String healthStatus,
            String vaccinationStatus,
            @PositiveOrZero Integer quantity,
            @NotNull @PositiveOrZero BigDecimal price,
            String status
    ) {
    }

    public record Response(
            UUID id,
            UUID shopId,
            UUID speciesId,
            UUID breedId,
            String name,
            String gender,
            Integer ageMonths,
            String color,
            BigDecimal weightKg,
            String description,
            String healthStatus,
            String vaccinationStatus,
            Integer quantity,
            BigDecimal price,
            String status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
    }
}
