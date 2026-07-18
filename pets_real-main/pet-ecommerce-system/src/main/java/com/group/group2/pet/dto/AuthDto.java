package com.group.group2.pet.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

public final class AuthDto {
    private AuthDto() {
    }

    public record LoginRequest(
            @Email(message = "cần địa chỉ email hợp lệ")
            @NotBlank(message = "cần địa chỉ email hợp lệ")
            String email,
            @NotBlank String password
    ) {
    }

    public record RegisterRequest(
            @NotBlank String fullName,
            @Email(message = "cần địa chỉ email hợp lệ")
            @NotBlank(message = "cần địa chỉ email hợp lệ")
            String email,
            String phone,
            @NotBlank String password
    ) {
    }

    public record AuthUser(
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

    public record AuthResponse(
            AuthUser user,
            String token
    ) {
    }
}
