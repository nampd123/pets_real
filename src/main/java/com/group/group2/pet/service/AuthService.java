package com.group.group2.pet.service;

import com.group.group2.pet.domain.UserEntity;
import com.group.group2.pet.dto.AuthDto;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.UserRepository;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {
    private final UserRepository repository;

    public AuthService(UserRepository repository, ApiMapper mapper) {
        this.repository = repository;
    }

    @Transactional
    public AuthDto.AuthResponse register(AuthDto.RegisterRequest request) {
        repository.findByEmail(request.email()).ifPresent(user -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        });

        UserEntity entity = new UserEntity();
        entity.setFullName(request.fullName());
        entity.setEmail(request.email());
        entity.setPhone(request.phone());
        entity.setPasswordHash(request.password());
        entity.setRole("CUSTOMER");
        entity.setStatus("ACTIVE");

        UserEntity saved = repository.save(entity);
        return new AuthDto.AuthResponse(toAuthUser(saved), createToken(saved.getId()));
    }

    @Transactional(readOnly = true)
    public AuthDto.AuthResponse login(AuthDto.LoginRequest request) {
        UserEntity entity = repository.findByEmail(request.email())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));

        if (!request.password().equals(entity.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        return new AuthDto.AuthResponse(toAuthUser(entity), createToken(entity.getId()));
    }

    private AuthDto.AuthUser toAuthUser(UserEntity entity) {
        return new AuthDto.AuthUser(
                entity.getId(),
                entity.getFullName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getRole(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }

    private String createToken(UUID userId) {
        return "demo-token-" + userId;
    }
}