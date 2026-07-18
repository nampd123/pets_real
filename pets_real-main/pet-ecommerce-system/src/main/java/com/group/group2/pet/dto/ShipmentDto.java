package com.group.group2.pet.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;

public final class ShipmentDto {
    private ShipmentDto() {
    }

    public record Request(
            @NotNull UUID orderId,
            String carrierName,
            String trackingCode,
            String shippingStatus,
            LocalDateTime shippedAt,
            LocalDateTime deliveredAt
    ) {
    }

    public record Response(
            UUID id,
            UUID orderId,
            String carrierName,
            String trackingCode,
            String shippingStatus,
            LocalDateTime shippedAt,
            LocalDateTime deliveredAt,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
    }
}
