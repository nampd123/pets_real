package com.group.group2.pet.repository;

import com.group.group2.pet.domain.ShipmentEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShipmentRepository extends JpaRepository<ShipmentEntity, UUID> {
}
