package com.group.group2.pet.repository;

import com.group.group2.pet.domain.PetImageEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetImageRepository extends JpaRepository<PetImageEntity, UUID> {
}
