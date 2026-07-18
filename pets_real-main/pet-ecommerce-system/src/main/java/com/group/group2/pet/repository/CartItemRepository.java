package com.group.group2.pet.repository;

import com.group.group2.pet.domain.CartItemEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {
}
