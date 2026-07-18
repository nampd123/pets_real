package com.group.group2.pet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

public final class UserDto {
    private UserDto() {
    }

    public record Request(
            @NotBlank String fullName,
            @Email @NotBlank String email,
            String phone,
            @NotBlank String passwordHash,
            @NotBlank String role,
            String status
    ) {
    }

    public record Response(
            UUID id,
            String fullName,
            String email,
            String phone,
            String role,
            String status,
            LocalDateTime createdAt,
            LocalDateTime updatedAt
    ) {
    }
}
