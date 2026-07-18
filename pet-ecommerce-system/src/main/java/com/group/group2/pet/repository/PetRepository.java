package com.group.group2.pet.repository;

import com.group.group2.pet.domain.PetEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<PetEntity, UUID> {

}

