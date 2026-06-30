package com.group.group2.pet.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public final class ReviewDto {
    private ReviewDto() {
    }

    public record Request(
            @NotNull UUID orderId,
            @NotNull UUID customerId,
            @NotNull UUID shopId,
            @NotNull @Min(1) @Max(5) Integer rating,
            String comment
    ) {
    }

    public record Response(
            UUID id,
            UUID orderId,
            UUID customerId,
            UUID shopId,
            Integer rating,
            String comment,
            LocalDateTime createdAt
    ) {
    }
}
