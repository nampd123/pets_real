package com.group.group2.pet.dto;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

public final class AddressDto {
    private AddressDto() {
    }

    public record Request(
            UUID userId,
            UUID shopId,
            String receiverName,
            String phone,
            @NotBlank String province,
            @NotBlank String district,
            @NotBlank String ward,
            @NotBlank String detailAddress,
            Boolean defaultAddress
    ) {
    }

    public record Response(
            UUID id,
            UUID userId,
            UUID shopId,
            String receiverName,
            String phone,
            String province,
            String district,
            String ward,
            String detailAddress,
            Boolean defaultAddress,
            LocalDateTime createdAt
    ) {
    }
}
