package com.group.group2.pet.repository;

import com.group.group2.pet.domain.FavoriteEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<FavoriteEntity, UUID> {
}
