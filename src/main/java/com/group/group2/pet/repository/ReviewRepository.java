package com.group.group2.pet.repository;

import com.group.group2.pet.domain.ReviewEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<ReviewEntity, UUID> {
}
