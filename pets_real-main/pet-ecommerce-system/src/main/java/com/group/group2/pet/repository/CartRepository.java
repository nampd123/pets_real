package com.group.group2.pet.repository;

import com.group.group2.pet.domain.CartEntity;
import java.util.UUID;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<CartEntity, UUID> {
	Optional<CartEntity> findByUserId(UUID userId);
}
