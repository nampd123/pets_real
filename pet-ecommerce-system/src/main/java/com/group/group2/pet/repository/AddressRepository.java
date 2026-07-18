package com.group.group2.pet.repository;

import com.group.group2.pet.domain.AddressEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<AddressEntity, UUID> {
}
