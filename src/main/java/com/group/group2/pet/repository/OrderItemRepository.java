package com.group.group2.pet.repository;

import com.group.group2.pet.domain.OrderItemEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity, UUID> {
}
