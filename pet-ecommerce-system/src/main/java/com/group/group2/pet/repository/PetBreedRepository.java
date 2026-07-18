package com.group.group2.pet.repository;

import com.group.group2.pet.domain.PetBreedEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetBreedRepository extends JpaRepository<PetBreedEntity, UUID> {
}
