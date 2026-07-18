package com.group.group2.pet.repository;

import com.group.group2.pet.domain.ShopEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<ShopEntity, UUID> {
}
