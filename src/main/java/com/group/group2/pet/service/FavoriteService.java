package com.group.group2.pet.service;

import com.group.group2.pet.domain.FavoriteEntity;
import com.group.group2.pet.dto.FavoriteDto;
import com.group.group2.pet.exception.ResourceNotFoundException;
import com.group.group2.pet.mapper.ApiMapper;
import com.group.group2.pet.repository.FavoriteRepository;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FavoriteService {
    private final FavoriteRepository repository;
    private final ApiMapper mapper;

    public FavoriteService(FavoriteRepository repository, ApiMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Transactional(readOnly = true)
    public List<FavoriteDto.Response> findAll() {
        return repository.findAll().stream().map(mapper::toFavoriteResponse).toList();
    }

    @Transactional(readOnly = true)
    public FavoriteDto.Response findById(UUID id) {
        return mapper.toFavoriteResponse(findEntityById(id));
    }

    @Transactional
    public FavoriteDto.Response create(FavoriteDto.Request request) {
        return mapper.toFavoriteResponse(repository.save(mapper.toFavoriteEntity(request)));
    }

    @Transactional
    public FavoriteDto.Response update(UUID id, FavoriteDto.Request request) {
        FavoriteEntity entity = findEntityById(id);
        mapper.updateFavoriteEntity(request, entity);
        return mapper.toFavoriteResponse(repository.save(entity));
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(findEntityById(id));
    }

    private FavoriteEntity findEntityById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Favorite", id));
    }
}
