package com.group.group2.pet.repository;

import com.group.group2.pet.domain.UserEntity;
import java.util.UUID;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
	Optional<UserEntity> findByEmail(String email);
}
