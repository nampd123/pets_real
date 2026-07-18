package com.group.group2.pet.service;

import com.group.group2.pet.domain.ReviewEntity;
import com.group.group2.pet.dto.ReviewDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.ReviewRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReviewService {
    private final ReviewRepository repository;
    private final ApiMapper mapper;

    public ReviewService(ReviewRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<ReviewDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toReviewResponse).toList();
    }

    @Transactional(readOnly = true)
    public ReviewDto.Response findById(UUID id) {
        return mapper.toReviewResponse(findEntityById(id));
    }

    @Transactional
    public ReviewDto.Response create(ReviewDto.Request request) {
        return mapper.toReviewResponse(repository.save(mapper.toReviewEntity(request)));
    }

    @Transactional
    public ReviewDto.Response update(UUID id, ReviewDto.Request request) {
        ReviewEntity entity = findEntityById(id);
        mapper.updateReviewEntity(request, entity);
        return mapper.toReviewResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private ReviewEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Review", id));
    }
}
