package com.group.group2.pet.repository;

import com.group.group2.pet.domain.PetSpeciesEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetSpeciesRepository extends JpaRepository<PetSpeciesEntity, UUID> {
}
